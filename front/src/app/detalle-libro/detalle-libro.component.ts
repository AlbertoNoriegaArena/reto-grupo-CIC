import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LibroService } from '../../libro.service';
import { Libro } from '../../libro';
import { CommonModule } from '@angular/common';
import { ModalComponent } from '../components/modal/modal.component';
import { FormularioLibrosComponent } from '../formulariolibros/formulariolibros.component';
import { PrestamoService } from '../../prestamo.service';
import { Prestamo } from '../../prestamo';

@Component({
  selector: 'app-detalle-libro',
  imports: [
    CommonModule,
    ModalComponent,
    FormularioLibrosComponent,
  ],
  templateUrl: './detalle-libro.component.html',
  styleUrl: './detalle-libro.component.scss'
})
export class DetalleLibroComponent implements OnInit {
  libro: Libro | undefined;
  isModalOpen = false;
  titleModal = 'Editar libro';
  isEditMode = true;
  libroSeleccionado: Libro = { item: { formato: {} } } as Libro;
  prestamos: Prestamo[] = [];

  constructor(
    private route: ActivatedRoute,
    private libroService: LibroService,
    private router: Router,
    private prestamoService: PrestamoService,
  ) { }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.libroService.buscarUno(id).subscribe((libro) => {
        this.libro = libro;
      });
    }

    // Llamada para cargar los préstamos disponibles
    this.prestamoService.getPrestamos().subscribe((prestamos) => {
      this.prestamos = prestamos;
    });
  }

  goToMainPage() {
    this.router.navigate(['/']);
  }

  goToLibros() {
    this.router.navigate(['/listalibros']);
  }

  irAlPrestamo(id: number) {
    const prestamo = this.prestamos.find((p) => p.item.id === id);

    if (prestamo) {
      // Si se encuentra el préstamo, redirige al detalle del préstamo
      this.router.navigate([`/detalleprestamo/${prestamo.id}`]);
    } else {
      console.error('No se encontró un préstamo para este libro.');
    }
  }

  actualizarRegistro(id: number) {
    if (!this.libro) return;
    this.libroSeleccionado = { ...this.libro };
    this.isModalOpen = true;
  }

  cerrarModal() {
    this.isModalOpen = false;
  }

  onFormSubmitted() {
    if (this.libroSeleccionado?.item.id) {
      this.libroService.buscarUno(this.libroSeleccionado.item.id).subscribe(
        (libroActualizada) => {
          this.libro = libroActualizada;
          this.cerrarModal();

        },
        (error) => {
          console.error("Error al actualizar el detalle del préstamo:", error);
        }
      );
    }
  }
}
