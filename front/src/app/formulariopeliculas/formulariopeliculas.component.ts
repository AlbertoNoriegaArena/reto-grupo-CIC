// src/app/formulariopeliculas/formulariopeliculas.component.ts
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Pelicula } from '../../pelicula';
import { PeliculaService } from '../../pelicula.service';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-formulariopeliculas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './formulariopeliculas.component.html',
  styleUrl: './formulariopeliculas.component.scss'
})
export class FormulariopeliculasComponent implements OnInit {
  formatos: { nombre: string; id: number }[] = [];
  tipoItemSeleccionado: string = "Pelicula";
  pelicula = { item: { formato: {} } } as Pelicula;
  erroresBackend: any = {};

  @Output() formSubmitted = new EventEmitter<void>();
  @Output() formClosed = new EventEmitter<void>();

  constructor(private peliculaService: PeliculaService, private router: Router, private http: HttpClient) { }

  onSubmit() {
    // Comprobar que el formulario es válido
    this.peliculaService.insertar(this.pelicula).subscribe({
      next: (respuesta) => {
        console.log('Película guardada con éxito', respuesta);
        this.erroresBackend = {};  // Limpiar errores si la petición fue exitosa
        this.resetForm();  // Llamar a reset para limpiar el formulario después de guardar
        this.formSubmitted.emit();  // Emitir el evento de éxito si es necesario
        this.formClosed.emit();  // Emitir el evento de cierre
      },
      error: (error) => {
        if (error.status === 400) {
          if (typeof error.error === 'string') {
            this.erroresBackend = { general: error.error }; // Guardamos error como un mensaje general
          } else {
            this.erroresBackend = error.error; // Guardamos los errores específicos de los campos
          }
        } else {
          console.error('Error en el servidor', error);
        }
      }
    });
  }

  closeForm() {
    this.resetForm();
    this.formClosed.emit();
  }

  resetForm() {
    this.pelicula = { item: { formato: {} } } as Pelicula;  // Reseteamos el objeto pelicula
    this.erroresBackend = {};  // Limpiamos los errores del backend
  }

  ngOnInit() {
    this.getFormatos();
  }

  getFormatos() {
    this.http.get<any[]>('http://localhost:4200/api/TipoItemFormatos').subscribe(
      (data) => {
        this.formatos = data
          .filter(item => item.tipoItem.nombre === this.tipoItemSeleccionado)
          .map(item => ({
            id: item.formato.id, // Use formato.id as value
            nombre: item.formato.nombre // Use formato.nombre as display name
          }));
      },
      (error) => {
        console.error('Error fetching formats:', error);
      }
    );
  }
}
