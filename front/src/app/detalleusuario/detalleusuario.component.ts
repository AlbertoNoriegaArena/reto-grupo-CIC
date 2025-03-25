import { NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Persona } from '../../persona';
import { ActivatedRoute } from '@angular/router'; // Import ActivatedRoute
import { PersonaService } from '../../persona.service'; // Import PersonaService

@Component({
  selector: 'app-detalleusuario',
  imports: [NgFor, NgIf],
  templateUrl: './detalleusuario.component.html',
  styleUrl: './detalleusuario.component.scss',
  standalone: true, // Add this line
})
export class DetalleUsuarioComponent implements OnInit {

  persona: Persona = {} as Persona; // Change this line

  // Variables de los préstamos
  mostrarTabla: boolean = false;
  verTodosTabla: boolean = false;
  tipoRepositorio: string = '';
  prestamos: any[] = [];
  todosPrestamos: any[] = [];

  // Datos de los repositorios de préstamos
  prestamosPorRepositorio: any = {
    peliculas: [
      { nombre: 'Matrix', tipo: 'Películas', formato: 'DVD', fechaPrestamo: '01/03/2025', fechaDevolucion: '15/03/2025' },
      { nombre: 'Inception', tipo: 'Películas', formato: 'Blu-Ray', fechaPrestamo: '10/03/2025', fechaDevolucion: '20/03/2025' }
    ],
    libros: [
      { nombre: 'Cien Años de Soledad', tipo: 'Libros', formato: 'Físico', fechaPrestamo: '05/03/2025', fechaDevolucion: '20/03/2025' },
      { nombre: '1984', tipo: 'Libros', formato: 'Físico', fechaPrestamo: '15/03/2025', fechaDevolucion: '25/03/2025' }
    ],
    musica: [
      { nombre: 'Thriller - Michael Jackson', tipo: 'Música', formato: 'Vinilo', fechaPrestamo: '10/03/2025', fechaDevolucion: '24/03/2025' },
      { nombre: 'Back in Black - AC/DC', tipo: 'Música', formato: 'CD', fechaPrestamo: '12/03/2025', fechaDevolucion: '22/03/2025' }
    ]
  };
usuario: any;

  constructor(private route: ActivatedRoute, private personaService: PersonaService) { } // Inject ActivatedRoute and PersonaService

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id'); // Get the id from the URL
    if (id) {
      this.personaService.getPersonaById(Number(id)).subscribe(persona => {
        this.persona = persona;
      });
    }
  }

  // Función para mostrar los préstamos según el repositorio
  verPrestamos(tipo: string): void {
    this.tipoRepositorio = tipo;
    this.prestamos = this.prestamosPorRepositorio[tipo];
    this.mostrarTabla = true;
    this.verTodosTabla = false;
  }

  // Función para ver el detalle de un préstamo
  verDetallePrestamo(prestamo: any): void {
    console.log('Ver detalle del préstamo', prestamo);
    // Aquí puedes agregar la lógica para redirigir a una página de detalles
  }

  // Función para ver todos los préstamos del usuario
  verTodosPrestamos(): void {
    this.todosPrestamos = [
      ...this.prestamosPorRepositorio.peliculas,
      ...this.prestamosPorRepositorio.libros,
      ...this.prestamosPorRepositorio.musica
    ];
    this.verTodosTabla = true;
    this.mostrarTabla = false;
  }

  // Función para volver a la vista anterior
  volver(): void {
    this.mostrarTabla = false;
    this.verTodosTabla = false;
  }

}
