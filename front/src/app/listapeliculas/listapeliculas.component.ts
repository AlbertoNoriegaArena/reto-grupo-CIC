import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { PeliculaService } from '../../pelicula.service';
import { Pelicula } from '../../pelicula';
import { Router } from '@angular/router';
import { TablaGenericaComponent } from '../components/tabla-generica/tabla-generica.component';

@Component({
  selector: 'app-listapeliculas',
  standalone: true,
  imports: [TablaGenericaComponent],
  templateUrl: './listapeliculas.component.html',
  styleUrls: ['./listapeliculas.component.scss'],
})
export class ListapeliculasComponent implements OnInit {
  peliculas: Pelicula[] = [];
  dataSource: MatTableDataSource<Pelicula> = new MatTableDataSource();

  displayedColumns: string[] = ['nombre',  'formato', 'director', 'duracion', 'fechaEstreno',  'acciones'];

  columnDefinitions = [
    { key: 'nombre', label: 'Nombre' },
    { key: 'formato', label: 'Formato' },
    { key: 'director', label: 'Director' },
    { key: 'duracion', label: 'Duración (minutos)' },
    { key: 'fechaEstreno', label: 'Fecha de Estreno' },
    
  ];

  constructor(private peliculaService: PeliculaService, private router: Router) {}

  ngOnInit(): void {
    this.loadPeliculas();
  }

  loadPeliculas() {
    this.peliculaService.getPeliculas().subscribe({
      next: (peliculas) => {
        this.peliculas = peliculas.map(p => ({
          ...p,
          nombre: p.item.nombre,
          formato: p.item.formato.nombre,
        }));
        this.dataSource.data = this.peliculas;
      },
      error: (error) => console.error('Error al cargar las películas:', error),
    });
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

}
