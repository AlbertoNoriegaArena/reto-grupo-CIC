import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { PrestamoService } from '../../prestamo.service';
import { Prestamo } from '../../prestamo';
import { Router } from '@angular/router';
import { TablaGenericaComponent } from '../components/tabla-generica/tabla-generica.component';
import { CommonModule } from '@angular/common';
import { FormularioprestamosComponent } from '../formularioprestamos/formularioprestamos.component';
import { ModalComponent } from '../components/modal/modal.component';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-listaprestamos',
  standalone: true,
  imports: [
    TablaGenericaComponent,
    CommonModule,
    FormularioprestamosComponent,
    ModalComponent,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    FormsModule,
  ],
  templateUrl: './listaprestamos.component.html',
  styleUrl: './listaprestamos.component.scss'
})
export class ListaprestamosComponent implements OnInit {

  prestamos: Prestamo[] = [];
  dataSource: MatTableDataSource<Prestamo> = new MatTableDataSource();
  isEditMode = false;
  isModalOpen = false;
  titleModal = 'Agregar Prestamo'; //Variable para el título dinámico

  prestamosFiltrados: 'todos' | 'activos' | 'vencidos' = 'todos';

  displayedColumns: string[] = [];
  columnDefinitions: any[] = [];

  @Output() prestamoDevuelto = new EventEmitter<void>();

  selectedPrestamo: Prestamo = { item: { formato: {} } } as Prestamo;

  // Todas las columnas (para "Todos")
  todosDisplayedColumns: string[] = ['nombreItem', 'nombrePersona', 'fechaPrestamo', 'fechaPrevistaDevolucion', 'fechaDevolucion', 'acciones'];
  todosColumnDefinitions = [
    { key: 'nombreItem', label: 'Artículo' },
    { key: 'nombrePersona', label: 'Prestado a' },
    { key: 'fechaPrestamo', label: 'Fecha de préstamo' },
    { key: 'fechaPrevistaDevolucion', label: 'Fecha prevista de devolución' },
    { key: 'fechaDevolucion', label: 'Fecha de Devolución' },
  ];

  // Sin "fechaDevolucion" (para activos y vencidos)
  reducedDisplayedColumns: string[] = ['nombreItem', 'nombrePersona', 'fechaPrestamo', 'fechaPrevistaDevolucion', 'acciones'];
  reducedColumnDefinitions = [
    { key: 'nombreItem', label: 'Artículo' },
    { key: 'nombrePersona', label: 'Prestado a' },
    { key: 'fechaPrestamo', label: 'Fecha de préstamo' },
    { key: 'fechaPrevistaDevolucion', label: 'Fecha prevista de devolución' },
  ];

  constructor(private prestamoService: PrestamoService, private router: Router, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.loadPrestamos();
  }

  loadPrestamos() {
    this.prestamosFiltrados = 'todos';
    this.displayedColumns = this.todosDisplayedColumns;
    this.columnDefinitions = this.todosColumnDefinitions;

    this.prestamoService.getPrestamos().subscribe({
      next: (prestamos) => {
        this.prestamos = prestamos;
        this.dataSource.data = prestamos;
      },
      error: (error) => console.error('Error al cargar los préstamos:', error),
    });
  }

  abrirModalAgregar() {

    this.isEditMode = false;
    this.titleModal = 'Agregar Préstamo'; //Cambiar el título
    this.isModalOpen = true;
  }

  abrirModalEditar(id: number) {
    const prestamo = this.dataSource.data.find(p => p.item.id === id);
    if (prestamo) {
      this.selectedPrestamo = { ...prestamo }; //Clonamos la película
      this.isEditMode = true;
      this.titleModal = 'Editar Préstamo'; //Cambiar el título
      this.isModalOpen = true;
    }
  }

  cerrarModal() {
    this.isModalOpen = false;
    this.titleModal = 'Agregar Préstamo'; //Resetear el título
  }

  onFormSubmitted() {
    this.cerrarModal();
    this.loadPrestamos();
  }

  goToMainPage() {
    this.router.navigate(['/']);
  }

  verDetalles(id: number) {
    this.router.navigate(['/detalleprestamo', id]); //Navegar a la página de detalles
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
        this.prestamoService.deletePrestamo(id).subscribe({
          next: () => {
            Swal.fire('Eliminado', 'Préstamo eliminado');
            this.loadPrestamos();
          },
          error: (error) => {
            const errorMessage = error.error?.mensaje || 'Error al eliminar el préstamo';
            Swal.fire('Error', errorMessage); // Muestra el mensaje de error
          },
        });
      }
    });
  }

  devolverPrestamo(id: number): void {
    this.prestamoService.devolverPrestamo(id).subscribe(
      () => {
        this.snackBar.open('Préstamo devuelto', 'Cerrar', { duration: 3000, verticalPosition: 'top', panelClass: ['custom-snackbar'] });
        this.loadPrestamos();
        this.prestamoDevuelto.emit();
      },
      (error) => {
        this.snackBar.open(error.message || 'Error al devolver el préstamo', 'Cerrar', { duration: 5000, verticalPosition: 'top', panelClass: ['custom-snackbar'] });
      }
    );
  }

  filtrarPrestamos(tipo: 'activos' | 'vencidos' | 'todos') {
    this.prestamosFiltrados = tipo;
  
    if (tipo === 'activos') {
      this.displayedColumns = this.reducedDisplayedColumns;
      this.columnDefinitions = this.reducedColumnDefinitions;
  
      this.prestamoService.getPrestamosActivos().subscribe({
        next: (data) => this.dataSource.data = data,
        error: (err) => console.error('Error al cargar préstamos activos', err)
      });
    } else if (tipo === 'vencidos') {
      this.displayedColumns = this.reducedDisplayedColumns;
      this.columnDefinitions = this.reducedColumnDefinitions;
  
      this.prestamoService.getPrestamosVencidos().subscribe({
        next: (data) => this.dataSource.data = data,
        error: (err) => console.error('Error al cargar préstamos vencidos', err)
      });
    } else {
      this.loadPrestamos(); // Esto ya setea las columnas completas
    }
  }
  
}
