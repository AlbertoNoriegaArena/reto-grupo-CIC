import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
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
  styleUrls: ['./formulariopeliculas.component.scss']
})
export class FormulariopeliculasComponent implements OnInit {
  formatos: { nombre: string; id: number }[] = [];
  tipoItemSeleccionado: string = "Pelicula";
  erroresBackend: any = {};

  @Output() formSubmitted = new EventEmitter<void>();
  @Output() formClosed = new EventEmitter<void>();

  @Input() isEditMode: boolean = false;
  @Input() pelicula: Pelicula = { 
     id: 0,
     nombre: '',
     director: '',
     duracion: 0,
     genero: '',
     fechaEstreno: '',
     item: { 
         id: 0,
         nombre: '',
         ubicacion: '',
         tipo: {
             id: 0,
             nombre: ''
         },
         formato: {
             id: 0,
             nombre: ''
         },
         fecha: '',
         estado: ''
     }
   };

  constructor(
    private peliculaService: PeliculaService,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit() {
    this.getFormatos(); // Llamada a la API para obtener los formatos
  }

  onSubmit() {
    if (this.isEditMode) {
      this.update();
    } else {
      this.create();
    }
  }

  create() {
    this.peliculaService.insertar(this.pelicula).subscribe({
        next: (respuesta) => {
          console.log('Película guardada con éxito', respuesta);
          this.erroresBackend = {}; // Limpiar errores si la petición fue exitosa
          this.resetForm();  // Limpiar el formulario después de guardar
          this.formSubmitted.emit();  // Emitir el evento de éxito
          this.formClosed.emit();  // Emitir el evento de cierre
        },
        error: (error) => {
          if (error.status === 400) {
            // Guardamos los errores del backend si los hay
            if (typeof error.error === 'string') {
              this.erroresBackend = { general: error.error };
            } else {
              this.erroresBackend = error.error;
            }
          } else {
            console.error('Error en el servidor', error);
          }
        }
      });
 }

 update() {
    this.peliculaService.actualizar(this.pelicula).subscribe({
        next: (respuesta) => {
          console.log('Película actualizada con éxito', respuesta);
          this.erroresBackend = {}; // Limpiar errores si la petición fue exitosa
          this.resetForm();  // Limpiar el formulario después de guardar
          this.formSubmitted.emit();  // Emitir el evento de éxito
          this.formClosed.emit();  // Emitir el evento de cierre
        },
        error: (error) => {
          if (error.status === 400) {
            // Guardamos los errores del backend si los hay
            if (typeof error.error === 'string') {
              this.erroresBackend = { general: error.error };
            } else {
              this.erroresBackend = error.error;
            }
          } else {
            console.error('Error en el servidor', error);
          }
        },
      });
 }

  closeForm() {
    this.resetForm();
    this.formClosed.emit();
  }

  resetForm() {
    // Limpiar el objeto 'pelicula' y los errores
    this.pelicula = { item: { formato: {} }, fechaEstreno: '' } as Pelicula;
    this.erroresBackend = {};  // Limpiar los errores
    this.isEditMode=false;
  }

  getFormatos() {
    // Obtener los formatos desde la API
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
