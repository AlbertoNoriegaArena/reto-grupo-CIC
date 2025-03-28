import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator, MatPaginatorIntl } from '@angular/material/paginator';
import { CommonModule, DatePipe } from '@angular/common';
import { PeliculaService } from '../../pelicula.service';
import { Pelicula } from '../../pelicula';
import { Router } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CustomPaginator } from '../custom-paginator';

@Component({
    selector: 'app-listapeliculas',
    standalone: true,
    imports: [
        CommonModule,
        DatePipe,
        MatTableModule,
        MatPaginatorModule,
        MatSortModule,
        MatInputModule,
        MatButtonModule,
    ],
    templateUrl: './listapeliculas.component.html',
    styleUrls: ['./listapeliculas.component.scss'],
    providers: [
        { provide: MatPaginatorIntl, useFactory: CustomPaginator } // Se aplica la traducción aquí
    ]
})


export class ListapeliculasComponent implements OnInit, AfterViewInit {
    peliculas: Pelicula[] = [];
    displayedColumns: string[] = ['nombre', 'director', 'duracion', 'fechaEstreno', 'formato', 'acciones'];
    dataSource: MatTableDataSource<Pelicula> = new MatTableDataSource();

    @ViewChild(MatSort) sort: MatSort = new MatSort();
    @ViewChild(MatPaginator) paginator: MatPaginator = new MatPaginator();

    constructor(private peliculaService: PeliculaService, private router: Router) {}

    ngOnInit(): void {
        this.loadPeliculas();
    }

    ngAfterViewInit() {
        this.dataSource.sort = this.sort;
        this.sort.disableClear = true; // Esto evita que vuelva al estado original
        this.dataSource.paginator = this.paginator;
    }

    loadPeliculas() {
        this.peliculaService.getPeliculas().subscribe({
            next: (peliculas) => {
                // Mapear las películas para crear propiedades planas
                this.peliculas = peliculas.map(pelicula => ({
                    ...pelicula,
                    nombre: pelicula.item.nombre,
                    formato: pelicula.item.formato.nombre
                }));
                this.dataSource.data = this.peliculas;
            },
            error: (error) => {
                console.error('Error al cargar las peliculas:', error);
            }
        });
    }

    applyFilter(event: Event): void {
        const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
        this.dataSource.filter = filterValue;

        if (this.dataSource.paginator) {
            this.dataSource.paginator.firstPage();
        }
    }

    goToFormularioPeliculas() {
        this.router.navigate(['/formulariopeliculas']);
    }

    goToMainPage() {
        this.router.navigate(['/']);
    }

    goToDetalles(pelicula: Pelicula) {
        this.router.navigate(['/detallespelicula', pelicula.item.id]);
    }

    goToActualizar(pelicula: Pelicula) {
        this.router.navigate(['/actualizarpelicula', pelicula.item.id]);
    }

    eliminarPelicula(pelicula: Pelicula) {
        if (confirm('¿Estás seguro de que deseas eliminar esta película?')) {
            this.peliculaService.borrar(pelicula.item.id).subscribe({
                next: () => {
                    this.peliculas = this.peliculas.filter(p => p.item.id !== pelicula.item.id);
                    alert('Película eliminada exitosamente.');
                },
                error: (error) => {
                    console.error('Error al eliminar la película:', error);
                    alert('Hubo un error al eliminar la película.');
                }
            });
        }
    }

     // Funciones de acceso a los datos de forma más sencilla
     getNombre(pelicula: Pelicula): string {
        return pelicula.item.nombre;
    }

    getFormato(pelicula: Pelicula): string {
        return pelicula.item.formato.nombre;
    }
}
