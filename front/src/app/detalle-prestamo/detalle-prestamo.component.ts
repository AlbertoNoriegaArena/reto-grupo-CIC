import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PrestamoService } from '../../prestamo.service';
import { Prestamo } from '../../prestamo';
import { CommonModule } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ModalComponent } from '../components/modal/modal.component';
import { FormularioprestamosComponent } from '../formularioprestamos/formularioprestamos.component';

@Component({
  selector: 'app-detalle-prestamo',
  standalone: true,
  imports: [
    CommonModule,
    ModalComponent,
    FormularioprestamosComponent,
  ],
  templateUrl: './detalle-prestamo.component.html',
  styleUrl: './detalle-prestamo.component.scss'
})
export class DetallePrestamoComponent implements OnInit {

  prestamo: Prestamo | undefined;
  isModalOpen = false;
  titleModal = 'Editar Préstamo';
  isEditMode = true;
  prestamoSeleccionado: Prestamo = {} as Prestamo; // Asegura que nunca sea undefined

  constructor(
    private route: ActivatedRoute,
    private prestamoService: PrestamoService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.prestamoService.getPrestamoById(id).subscribe((prestamo) => {
        this.prestamo = prestamo;
      });
    }
  }

  goToMainPage() {
    this.router.navigate(['/']);
  }

  goToPrestamos() {
    this.router.navigate(['/listaprestamos']);
  }

  devolverPrestamo(id: number): void {
    this.prestamoService.devolverPrestamo(id).subscribe(
      (response) => {
        // Mostrar mensaje de éxito
        this.snackBar.open('Préstamo devuelto', 'Cerrar', { duration: 3000, verticalPosition: 'top', panelClass: ['custom-snackbar'] });

        // Aquí, en lugar de actualizar el objeto prestamo directamente, 
        // se puede hacer una nueva solicitud para obtener el detalle actualizado del préstamo
        this.actualizarDetallePrestamo(id);

      },
      (error) => {
        // Mostrar mensaje de error
        this.snackBar.open(error.message || 'Error al devolver el préstamo', 'Cerrar', { duration: 5000, verticalPosition: 'top', panelClass: ['custom-snackbar'] });
      }
    );
  }

  // Función para obtener el detalle actualizado del préstamo
  actualizarDetallePrestamo(id: number): void {
    this.prestamoService.getPrestamoById(id).subscribe(
      (prestamoActualizado) => {
        this.prestamo = prestamoActualizado;  // Actualizamos el objeto prestamo con los nuevos datos
      },
      (error) => {
        this.snackBar.open('Error al actualizar el detalle del préstamo', 'Cerrar', { duration: 5000, verticalPosition: 'top', panelClass: ['custom-snackbar'] });
      }
    );
  }

  actualizarRegistro(id: number) {
    if (!this.prestamo) return;
    this.prestamoSeleccionado = { ...this.prestamo };
    this.isModalOpen = true;
  }

  cerrarModal() {
    this.isModalOpen = false;
  }

  onFormSubmitted() {
    // Recargar el préstamo actualizado desde el servicio
    if (this.prestamoSeleccionado?.id) {
      this.prestamoService.getPrestamoById(this.prestamoSeleccionado.id).subscribe({
        next: (prestamoActualizado) => {
          this.prestamo = prestamoActualizado; // Actualizar la vista con los nuevos datos
          this.cerrarModal(); // Cerrar el modal
        },
        error: (error) => {
          console.error("Error al actualizar el detalle del préstamo:", error);
        }
      });
    }
  }
}
