import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { PeliculaService } from '../../services/pelicula.service';
import { Pelicula } from '../../models/pelicula';
import { Router } from '@angular/router';
import { TablaGenericaComponent } from '../tabla-generica/tabla-generica.component';
import { CommonModule } from '@angular/common';
import { FormulariopeliculasComponent } from '../formulariopeliculas/formulariopeliculas.component';
import { ModalComponent } from '../modal/modal.component';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-listapeliculas',
  standalone: true,
  imports: [
    TablaGenericaComponent,
    CommonModule,
    FormulariopeliculasComponent,
    ModalComponent,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    FormsModule,
  ],
  templateUrl: './listapeliculas.component.html',
  styleUrls: ['./listapeliculas.component.scss'],
})
export class ListapeliculasComponent implements OnInit {
  peliculas: Pelicula[] = [];
  dataSource: MatTableDataSource<Pelicula> = new MatTableDataSource();
  isEditMode = false;
  isModalOpen = false;
  titleModal = 'Agregar Película'; //Variable para el título dinámico

  selectedPelicula: Pelicula = { item: { formato: {} } } as Pelicula;

  displayedColumns: string[] = ['nombre', 'formato', 'director', 'duracion', 'fechaEstreno'];

  columnDefinitions = [
    { key: 'nombre', label: 'Nombre' },
    { key: 'formato', label: 'Formato' },
    { key: 'director', label: 'Director' },
    { key: 'duracion', label: 'Duración (minutos)' },
    { key: 'fechaEstreno', label: 'Fecha de Estreno' },
  ];

  constructor(private peliculaService: PeliculaService, private router: Router) { }

  ngOnInit(): void {
    this.loadPeliculas();
  }

  loadPeliculas() {
    this.peliculaService.getPeliculas().subscribe({
      next: (peliculas) => {
        this.peliculas = peliculas.map(p => ({
          ...p,
          estado: p.item?.estado ?? ''
        }));
        this.dataSource.data = this.peliculas;
      },
      error: (error) => {
        console.error('Error al cargar las películas:', error);
      }
    });
  }

  abrirModalAgregar() {
    
    this.isEditMode = false;
    this.titleModal = 'Agregar Película'; //Cambiar el título
    this.isModalOpen = true;
  }

  abrirModalEditar(id: number) {
    const pelicula = this.dataSource.data.find(p => p.item.id === id);
    if (pelicula) {
      this.selectedPelicula = { ...pelicula }; //Clonamos la película
      this.isEditMode = true;
      this.titleModal = 'Editar Película'; //Cambiar el título
      this.isModalOpen = true;
    }
  }

  cerrarModal() {
    this.isModalOpen = false;
    this.titleModal = 'Agregar Película'; //Resetear el título
  }

  onFormSubmitted() {
    this.cerrarModal();
    this.loadPeliculas(); 
  }

  goToMainPage() {
    this.router.navigate(['/']);
  }

  verDetalles(id: number) {
    this.router.navigate(['/detallepelicula', id]); //Navegar a la página de detalles
  }

  delete(id: number) {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Este registro será eliminado y no lo podrás recuperar',
      showCancelButton: true,
      confirmButtonText: 'Eliminar',
      confirmButtonColor: '#d33', 
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.peliculaService.borrar(id).subscribe({
          next: () => {
            Swal.fire('Eliminado', 'Película eliminada correctamente');
            this.loadPeliculas();
          },
          error: (error) => Swal.fire('Error', error.message),
        });
      }
    });
  }

}
