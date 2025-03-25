// src/app/listapeliculas/listapeliculas.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PeliculaService } from '../../pelicula.service';
import { Pelicula } from '../../pelicula';
import { Router } from '@angular/router';


@Component({
    selector: 'app-listapeliculas',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './listapeliculas.component.html',
    styleUrl: './listapeliculas.component.scss'
})
export class ListapeliculasComponent implements OnInit {
    peliculas: Pelicula[] = [];

    constructor(private peliculaService: PeliculaService, private router: Router) { }

    ngOnInit(): void {
        this.loadPeliculas();
    }

    loadPeliculas() {
        this.peliculaService.getPeliculas().subscribe({
            next: (peliculas) => {
                this.peliculas = peliculas;
            },
            error: (error) => {
                console.error('Error al cargar las peliculas:', error);
            }
        });
    }
    goToFormularioPeliculas() {
        this.router.navigate(['/formulariopeliculas']);
    }
    goToMainPage() {
        this.router.navigate(['/']);
    }

    // Método para redirigir a la página de detalles de la película
    goToDetalles(pelicula: Pelicula) {
        this.router.navigate(['/detallespelicula', pelicula.item.id]); // Asumiendo que la película tiene un ID
    }

    // Método para redirigir a la página de actualización de la película
    goToActualizar(pelicula: Pelicula) {
        this.router.navigate(['/actualizarpelicula', pelicula.item.id]); // Asumiendo que la película tiene un ID
    }

    // Método para eliminar una película
    eliminarPelicula(pelicula: Pelicula) {
        if (confirm('¿Estás seguro de que deseas eliminar esta película?')) {
            this.peliculaService.borrar(pelicula.item.id).subscribe({
                next: () => {
                    this.peliculas = this.peliculas.filter(p => p.item.id !== pelicula.item.id); // Actualiza la lista
                    alert('Película eliminada exitosamente.');
                },
                error: (error) => {
                    console.error('Error al eliminar la película:', error);
                    alert('Hubo un error al eliminar la película.');
                }
            });
        }
    }
}
