import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Formato } from '../../formato';
import { FormatoService } from '../../formato.service';

@Component({
  selector: 'app-formularioformatos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './formularioformatos.component.html',
  styleUrl: './formularioformatos.component.scss'
})
export class FormularioformatosComponent implements OnInit {
  @Output() formSubmitted = new EventEmitter<void>();
  @Output() formClosed = new EventEmitter<void>();
  @Input() isEditMode: boolean = false;
  @Input() formato!: Formato;
  erroresBackend: any = {};
  tiposItem: any = [];
  selectedTipoItemId: number = 0;

  constructor(private formatoService: FormatoService) { }

  ngOnInit() {
    if (!this.formato) {
      this.formato = { id: 0, nombre: '', tipoItem: { id: 0, nombre: '' } };
    }
    if (this.formato.tipoItem) {
      this.selectedTipoItemId = this.formato.tipoItem.id;
    }
    this.loadTiposItem();
  }

  loadTiposItem() {
    this.formatoService.getTipoItems().subscribe({
      next: (tiposItem) => {
        this.tiposItem = tiposItem;
      },
      error: (err) => {
        console.error('Error al cargar los tipos de item', err);
      }
    });
  }

  onSubmit() {
    if (this.isEditMode && this.formato.id !== 0) {
      this.update();
    } else {
      this.create();
    }
  }

  update() {
    const nombre = this.formato.nombre?.trim();
  
    if (!nombre) {
      this.erroresBackend.general = 'Debe completar todos los campos';
      return;
    }
  
    this.formatoService.actualizar(this.formato).subscribe({
      next: () => {
        this.resetForm();
        this.formSubmitted.emit();
        this.formClosed.emit();
      },
      error: (errorFormato) => {
        this.handleBackendErrors(errorFormato);
      }
    });
  }
  

  create() {
    this.formato.tipoItem = this.tiposItem.find(
      (tipo: any) => tipo.id === Number(this.selectedTipoItemId)
    );

    const tipoItemId = this.formato.tipoItem?.id;
    const nombre = this.formato.nombre?.trim();

    if (!tipoItemId || !nombre) {
      this.erroresBackend.general = 'Debe completar todos los campos';
      return;
    }

    //Crear el formato
    this.formatoService.insertar({
      nombre,
      id: 0
    }).subscribe({
      next: (formatoCreado) => {
        const formatoId = formatoCreado.id;

        // crear relación con TipoItemFormato
        this.formatoService.crearRelacionTipoItemFormato(tipoItemId, formatoId).subscribe({
          next: () => {
            this.resetForm();
            this.formSubmitted.emit();
            this.formClosed.emit();
          },
          error: (errorRelacion) => {
            this.handleBackendErrors(errorRelacion);
          }
        });
      },
      error: (errorFormato) => {
        this.handleBackendErrors(errorFormato);
      }
    });
  }

  handleBackendErrors(error: any) {
    if (error.status === 400 && error.error?.errors) {
      this.erroresBackend = error.error.errors.reduce((acc: any, curr: any) => {
        acc[curr.field] = curr.defaultMessage;
        return acc;
      }, {});
    } else if (error.error?.mensaje) {
      // Si el backend manda un mensaje claro, lo usamos
      this.erroresBackend = { nombre: error.error.mensaje };
    } else if (typeof error.error === 'string') {
      // Si es solo un string
      this.erroresBackend = { nombre: error.error };
    } else {
      // Fallback por si no hay nada útil
      this.erroresBackend = { general: 'Ocurrió un error inesperado' };
    }
  }


  closeForm() {
    this.resetForm();
    this.formClosed.emit();
  }

  resetForm() {
    this.formato = { id: 0, nombre: '', tipoItem: { id: 0, nombre: '' } };
    this.erroresBackend = {};
    this.selectedTipoItemId = 0;
  }
}
