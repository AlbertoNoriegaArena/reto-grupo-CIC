// src/app/formulariolibros/formulariolibros.component.ts
import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Libro } from '../../libro';
import { LibroService } from '../../libro.service';

@Component({
  selector: 'app-formulariolibros',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './formulariolibros.component.html',
  styleUrl: './formulariolibros.component.scss'
})
export class FormularioLibrosComponent implements OnInit {
  formatos: { nombre: string; id: number }[] = [];
  tipoItemSeleccionado: string = "Libro";
  erroresBackend: any = {};

  @Output() formSubmitted = new EventEmitter<void>();
  @Output() formClosed = new EventEmitter<void>();

  @Input() isEditMode: boolean = false;
  @Input() libro!: Libro;

  constructor(private libroService: LibroService,) { }

  ngOnInit() {
    this.getFormatos(); // Llamada a la API para obtener los formatos
  }

  // Llamada al servicio para obtener los formatos
  getFormatos() {
    this.libroService.getFormatos(this.tipoItemSeleccionado).subscribe({
      next: (data) => {
        this.formatos = data;
      },
      error: (error) => {
        console.error('Error fetching formats:', error);
      }
    });
  }

  onSubmit() {
    if (this.isEditMode) {
      this.update();
    } else {
      this.create();
    }
  }

  create() {
    this.libroService.insertar(this.libro).subscribe({
      next: (respuesta) => {
        console.log('Libro guardado con éxito', respuesta);
        this.resetForm();  // Limpiar el formulario después de guardar
        this.formSubmitted.emit();  // Emitir el evento de éxito
        this.formClosed.emit();  // Emitir el evento de cierre
      },
      error: (error) => {
        this.handleBackendErrors(error);
      }
    });
  }

  update() {
    this.libroService.actualizar(this.libro).subscribe({
      next: (respuesta) => {
        console.log('Libro actualizada con éxito', respuesta);
        this.resetForm();  // Limpiar el formulario después de guardar
        this.formSubmitted.emit();  // Emitir el evento de éxito
        this.formClosed.emit();  // Emitir el evento de cierre
      },
      error: (error) => {
        this.handleBackendErrors(error);
      }
    });
  }

  // Función reutilizable para manejar errores del backend
  handleBackendErrors(error: any) {
    if (error.status === 400 && error.error?.errors) {
      this.erroresBackend = error.error.errors.reduce((acc: any, curr: any) => {
        acc[curr.field] = curr.defaultMessage; // Asegura que usas `defaultMessage`
        return acc;
      }, {});
    } else {
      this.erroresBackend = { general: 'El nombre y el formato son obligatorios' };
    }
  }

  closeForm() {
    this.resetForm();
    this.formClosed.emit();
  }

  resetForm() {
    // Limpiar el objeto 'libro' 
    this.libro = { item: { formato: {} }, } as Libro;
    this.erroresBackend = {};  // Limpiar los errores
  }
}
