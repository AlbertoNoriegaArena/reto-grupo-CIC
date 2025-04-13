// src/app/listalibros/listalibros.component.ts
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { LibroService } from '../../services/libro.service';
import { Libro } from '../../models/libro';
import { Router } from '@angular/router';
import { TablaGenericaComponent } from '../tabla-generica/tabla-generica.component';
import { CommonModule } from '@angular/common';
import { FormularioLibrosComponent } from '../formulariolibros/formulariolibros.component';
import { ModalComponent } from '../modal/modal.component';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';



@Component({
  selector: 'app-listalibros',
  standalone: true,
  imports: [
    TablaGenericaComponent,
    CommonModule,
    FormularioLibrosComponent,
    ModalComponent,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    FormsModule,
  ],
  templateUrl: './listalibros.component.html',
  styleUrl: './listalibros.component.scss'
})
export class ListalibrosComponent implements OnInit {
  libros: Libro[] = [];
  dataSource: MatTableDataSource<Libro> = new MatTableDataSource();
  isEditMode = false;
  isModalOpen = false;
  titleModal = 'Agregar Libro'; //Variable para el título dinámico

  selectedLibro: Libro = { item: { formato: {} } } as Libro;

  displayedColumns: string[] = ['nombre', 'formato', 'autor', 'isbn', 'editorial', 'acciones'];

  columnDefinitions = [
    { key: 'nombre', label: 'Nombre' },
    { key: 'formato', label: 'Formato' },
    { key: 'autor', label: 'Autor' },
    { key: 'isbn', label: 'Isbn' },
    { key: 'editorial', label: 'Editorial' },
  ];

  constructor(private libroService: LibroService, private router: Router) { }

  ngOnInit(): void {
    this.loadLibros();
  }

  loadLibros() {
    this.libroService.getLibros().subscribe({
      next: (libros) => {
        this.libros = libros;
        this.dataSource.data = this.libros;
      },
      error: (error) => {
        console.error('Error al cargar los libros:', error);
      }
    });
  }

  abrirModalAgregar() {

    this.isEditMode = false;
    this.titleModal = 'Agregar Libro'; //Cambiar el título
    this.isModalOpen = true;
  }

  abrirModalEditar(id: number) {
    const libro = this.dataSource.data.find(l => l.item.id === id);
    if (libro) {
      this.selectedLibro = { ...libro }; //Clonamos la película
      this.isEditMode = true;
      this.titleModal = 'Editar Libro'; //Cambiar el título
      this.isModalOpen = true;
    }
  }

  cerrarModal() {
    this.isModalOpen = false;
    this.titleModal = 'Agregar Libro'; //Resetear el título
  }

  onFormSubmitted() {
    this.cerrarModal();
    this.loadLibros();
  }

  goToMainPage() {
    this.router.navigate(['/']);
  }

  verDetalles(id: number) {
    this.router.navigate(['/detallelibro', id]); //Navegar a la página de detalles
  }

  delete(id: number) {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Este registro será eliminado y no lo podrás recuperar',
      showCancelButton: true,
      confirmButtonText: 'Eliminar',
      confirmButtonColor: '#d33',
      cancelButtonText: 'Cancelar',
      reverseButtons: false
    }).then((result) => {
      if (result.isConfirmed) {
        this.libroService.borrar(id).subscribe({
          next: () => {
            Swal.fire('Eliminado', 'Libro eliminado correctamente');
            this.loadLibros();
          },
          error: (error) => Swal.fire('Error', error.message),
        });
      }
    });
  }


}
