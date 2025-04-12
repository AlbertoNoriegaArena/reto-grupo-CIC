import { Component, OnInit, Output, EventEmitter, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Item } from '../../item';
import { ItemService } from '../../item.service';
import { FormatoService } from '../../formato.service';
import { forkJoin } from 'rxjs';
import { ItemDTO } from '../../itemDTO';


@Component({
  selector: 'app-formularioitems',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './formularioitems.component.html',
  styleUrl: './formularioitems.component.scss'
})
export class FormularioItemsComponent implements OnInit, OnChanges {
  @Output() formSubmitted = new EventEmitter<void>();
  @Output() formClosed = new EventEmitter<void>();

  @Input() isEditMode: boolean = false;
  @Input() item: Item = this.createEmptyItem();

  tipos: { id: number; nombre: string }[] = [];
  formatos: { id: number; nombre: string }[] = [];
  relaciones: any[] = [];
  erroresBackend: any = {};

  constructor(
    private itemService: ItemService,
    private formatoService: FormatoService
  ) { }

  ngOnInit() {
    this.loadTiposYRelaciones();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['item'] && this.item?.tipo?.id) {
      this.updateFormatosSegunTipo(this.item.tipo.id);
    }
  }

  loadTiposYRelaciones() {
    forkJoin({
      tipos: this.formatoService.getTipoItems(),
      relaciones: this.formatoService.getFormatosAgrupadosPorTipo()
    }).subscribe({
      next: ({ tipos, relaciones }) => {
        this.tipos = tipos;
        this.relaciones = relaciones;

        if (this.isEditMode && this.item.tipo?.id) {
          this.updateFormatosSegunTipo(this.item.tipo.id);
        }
      },
      error: (error) => {
        this.erroresBackend = {
          general: 'Error al cargar tipos y formatos.'
        };
        console.error(error);
      }
    });
  }

  onTipoChange() {
    this.item.formato = { id: 0, nombre: '' };
    if (this.item.tipo?.id) {
      this.updateFormatosSegunTipo(this.item.tipo.id);
    } else {
      this.formatos = [];
    }
  }

  updateFormatosSegunTipo(tipoId: number) {
    this.formatos = this.relaciones
      .filter(rel => Number(rel.tipoItem?.id) === Number(tipoId))
      .map(rel => rel.formato)
      .filter((formato, index, self) =>
        index === self.findIndex(f => f.id === formato.id)
      );
  }


  onSubmit() {
    if (!this.item.tipo?.id || !this.item.formato?.id) {
      this.erroresBackend = { general: 'Debe seleccionar un tipo y un formato válidos.' };
      return;
    }

    // Verifica si el campo estado está vacío y asigna el valor por defecto
    if (!this.item.estado) {
      this.item.estado = 'DISPONIBLE'; // Asigna el valor predeterminado de la enumeración EstadoItem
    }

    // Preparamos el objeto que se enviará al backend en formato ItemDTO
    const itemAEnviarCreate: ItemDTO = {
      nombre: this.item.nombre,
      tipoId: this.item.tipo.id,      
      formatoId: this.item.formato.id, 
      ubicacion: this.item.ubicacion,
      fecha: this.item.fecha,
      estado: this.item.estado
    };

    const tipoSeleccionado = this.tipos.find(t => t.id === Number(this.item.tipo.id));
    const nombreTipo = tipoSeleccionado?.nombre.toLowerCase();

    switch (nombreTipo) {
      case 'libro':
        Object.assign(itemAEnviarCreate, {
          autor: this.item.autor,
          isbn: this.item.isbn,
          editorial: this.item.editorial,
          numeroPaginas: this.item.numeroPaginas ? Number(this.item.numeroPaginas) : undefined,
          fechaPublicacion: this.item.fechaPublicacion
        });
        break;

      case 'pelicula':
        Object.assign(itemAEnviarCreate, {
          director: this.item.director,
          duracionPelicula: this.item.duracionPelicula ? Number(this.item.duracionPelicula) : undefined,
          generoPelicula: this.item.generoPelicula,
          fechaEstreno: this.item.fechaEstreno
        });
        break;

      case 'musica':
        Object.assign(itemAEnviarCreate, {
          generoMusica: this.item.generoMusica,
          cantante: this.item.cantante,
          album: this.item.album,
          duracionMusica: this.item.duracionMusica
        });
        break;
    }

    // Preparamos el objeto que se enviará al backend
    const itemAEnviarUpdate: Item = {
      id: this.isEditMode ? this.item.id : 0,  // Solo se incluye el id para la actualización
      nombre: this.item.nombre,
      ubicacion: this.item.ubicacion,
      fecha: this.item.fecha,
      estado: this.item.estado,
      tipo: this.item.tipo,  // Enviamos el objeto completo tipo
      formato: this.item.formato  // Enviamos el objeto completo formato
    };

    const llamada = this.isEditMode
      ? this.itemService.actualizar(itemAEnviarUpdate)  // En la edición ya pasamos el objeto con el id
      : this.itemService.insertar(itemAEnviarCreate);  // Para la creación, pasamos el objeto sin id

    llamada.subscribe({
      next: () => {
        this.resetForm();
        this.formSubmitted.emit();
        this.formClosed.emit();
      },
      error: (error) => this.handleBackendErrors(error)
    });
  }

  handleBackendErrors(error: any) {
    if (error.status === 400 && error.error?.parametros) {
      this.erroresBackend = error.error.parametros;  // Asignamos directamente los errores que nos llega
    } else {
      this.erroresBackend = {
        general: 'Error inesperado al guardar el ítem.'
      };
    }
  }

  closeForm() {
    this.resetForm();
    this.formClosed.emit();
  }

  resetForm() {
    this.item = this.createEmptyItem();
    this.formatos = [];
    this.erroresBackend = {};
  }

  createEmptyItem(): Item {
    return {
      id: 0,
      nombre: '',
      ubicacion: '',
      fecha: '',
      estado: '',
      tipo: { id: 0, nombre: '' },
      formato: { id: 0, nombre: '' },

      // libro
      autor: '',
      isbn: '',
      editorial: '',
      numeroPaginas: 0,
      fechaPublicacion: '',

      // película
      director: '',
      duracionPelicula: 0,
      generoPelicula: '',
      fechaEstreno: '',

      // música
      generoMusica: '',
      cantante: '',
      album: '',
      duracionMusica: ''
    };
  }


  esDeTipo(nombre: string): boolean {
    // Verifica que haya un tipo seleccionado (item.tipo y item.tipo.id no sean nulos/undefined/0)
    if (!this.item?.tipo?.id) {
      return false;
    }
    const selectedIdAsNumber = Number(this.item.tipo.id);

    // Busca el objeto 'tipo' completo usando el id numérico
    const tipoSeleccionado = this.tipos.find(t => t.id === selectedIdAsNumber);

    // Si se encontró el tipo, compara su nombre (en minúsculas) con el nombre buscado (en minúsculas)
    return tipoSeleccionado ? tipoSeleccionado.nombre.toLowerCase() === nombre.toLowerCase() : false;
  }

}
