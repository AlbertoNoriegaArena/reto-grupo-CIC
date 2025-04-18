import { Component, OnInit, Output, EventEmitter, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Item } from '../../models/item';
import { ItemService } from '../../services/item.service';
import { FormatoService } from '../../services/formato.service';
import { forkJoin } from 'rxjs';
import { ItemDTO } from '../../models/itemDTO';


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
  // Hacemos una copia profunda al recibir el Input para no modificar el original directamente
  _item: Item = this.createEmptyItem();
  @Input() set item(value: Item) {
    this._item = value ? { ...value, tipo: { ...value.tipo }, formato: { ...value.formato } } : this.createEmptyItem();
    // Si estamos en modo edición y hay un tipo, cargamos los formatos correspondientes
    if (this.isEditMode && this._item.tipo?.id) {
       this.updateFormatosSegunTipo(this._item.tipo.id);
    }
  }
  get item(): Item {
    return this._item;
  }

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
    // Si el item cambia (por ejemplo, al abrir el modal de edición)
    // y estamos en modo edición con un tipo válido, actualizamos los formatos.
    if (changes['item'] && this.isEditMode && this.item?.tipo?.id) {
        // Asegurarnos que las relaciones ya están cargadas antes de actualizar formatos
        if (this.relaciones.length > 0) {
             this.updateFormatosSegunTipo(this.item.tipo.id);
        }
    }
     // Si cambiamos de modo edición a añadir o viceversa
     if (changes['isEditMode'] && !this.isEditMode) {
        this.resetForm(); // Resetea si pasamos a modo 'añadir'
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
    // Aseguramos que tipoId sea un número para la comparación estricta
    const tipoIdNumerico = Number(tipoId);
    this.formatos = this.relaciones
      .filter(rel => Number(rel.tipoItem?.id) === tipoIdNumerico) // Comparamos como números
      .map(rel => rel.formato)
      .filter(formato => formato && typeof formato.id !== 'undefined' && formato.nombre) // Filtramos formatos inválidos
      .filter((formato, index, self) => // Eliminamos duplicados por id
        index === self.findIndex(f => f.id === formato.id)
      );

     // Opcional: Si el formato actual del item no está en la nueva lista, resetearlo
     if (this.item.formato?.id && !this.formatos.some(f => f.id === this.item.formato.id)) {
        this.item.formato = { id: 0, nombre: '' };
     }
  }

  onSubmit() {
    this.erroresBackend = {}; // Limpiar errores previos

    if (!this.item.tipo?.id || !this.item.formato?.id) {
      this.erroresBackend = { general: 'Debe seleccionar un tipo y un formato válidos.' };
      return;
    }

    // Asigna 'DISPONIBLE' si el estado está vacío
    if (!this.item.estado) {
      this.item.estado = 'DISPONIBLE';
    }

    // --- Construcción del objeto a enviar ---
    // Usaremos una estructura flexible que funcione para ambos casos (crear/editar)
    // Podrías refinar ItemDTO para incluir todos los campos opcionales si lo prefieres.
    const itemParaEnviar: any = {
        // Campos comunes siempre presentes
        nombre: this.item.nombre,
        ubicacion: this.item.ubicacion,
        fecha: this.item.fecha,
        estado: this.item.estado,
        // Para creación usamos Ids, para actualización podríamos necesitar objetos completos
        // dependiendo del backend, pero enviar Ids suele ser más común y robusto.
        tipoId: Number(this.item.tipo.id),
        formatoId: Number(this.item.formato.id),
        // Añadimos el ID solo si estamos en modo edición
        ...(this.isEditMode && { id: this.item.id })
    };

    const tipoSeleccionado = this.tipos.find(t => t.id === Number(this.item.tipo.id));
    const nombreTipo = tipoSeleccionado?.nombre.toLowerCase();

    switch (nombreTipo) {
      case 'libro':
        Object.assign(itemParaEnviar, {
          autor: this.item.autor,
          isbn: this.item.isbn,
          editorial: this.item.editorial,
          numeroPaginas: this.item.numeroPaginas ? Number(this.item.numeroPaginas) : undefined,
          fechaPublicacion: this.item.fechaPublicacion
        });
        break;

      case 'pelicula':
      case 'película': // Considerar acentos
        Object.assign(itemParaEnviar, {
          director: this.item.director,
          duracionPelicula: this.item.duracionPelicula ? Number(this.item.duracionPelicula) : undefined,
          generoPelicula: this.item.generoPelicula,
          fechaEstreno: this.item.fechaEstreno
        });
        break;

      case 'musica':
      case 'música': // Considerar acentos
        Object.assign(itemParaEnviar, {
          generoMusica: this.item.generoMusica,
          cantante: this.item.cantante,
          album: this.item.album,
          duracionMusica: this.item.duracionMusica // Asumiendo que es string, si no Number()
        });
        break;
    }

    // --- Llamada al servicio ---
    // Nota: Asegúrate que ItemService.actualizar espera un objeto compatible con itemParaEnviar.
    // Si ItemService.actualizar espera un objeto Item completo (con tipo y formato como objetos),
    // necesitarás ajustar itemParaEnviar o el método del servicio.
    // Asumiendo que el backend (y por tanto el servicio) puede manejar este DTO para actualizar:
    const llamada = this.isEditMode
      ? this.itemService.actualizar(itemParaEnviar as ItemDTO) 
      : this.itemService.insertar(itemParaEnviar as ItemDTO);

    llamada.subscribe({
      next: () => {
        this.resetForm();
        this.formSubmitted.emit(); // Avisa al padre que se completó
        // No necesitas emitir formClosed aquí si formSubmitted ya cierra el modal
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
      numeroPaginas: undefined,
      fechaPublicacion: '',

      // película
      director: '',
      duracionPelicula: undefined,
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
