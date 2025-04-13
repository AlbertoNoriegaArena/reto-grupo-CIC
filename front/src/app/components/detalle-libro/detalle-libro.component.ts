import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LibroService } from '../../services/libro.service';
import { Libro } from '../../models/libro';
import { CommonModule } from '@angular/common';
import { ModalComponent } from '../modal/modal.component';
import { FormularioLibrosComponent } from '../formulariolibros/formulariolibros.component';
import { PrestamoService } from '../../services/prestamo.service';
import { Prestamo } from '../../models/prestamo';

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
  ultimosPrestamos: Prestamo[] = [];
  prestamoActivo: Prestamo | null = null;

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
  
        const itemId = libro?.item?.id;
        if (itemId) {
          this.prestamoService.getUltimosPrestamosPorItem(itemId).subscribe(
            (prestamos) => {
              this.ultimosPrestamos = prestamos;
              this.prestamoActivo = this.ultimosPrestamos.find(p => !p.fechaDevolucion) || null;
            },
            (error) => {
              console.error('Error al obtener los últimos préstamos:', error);
            }
          );
        }
      });
    }
  
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
    this.router.navigate([`/detalleprestamo/${id}`]);
  }

  getPrestamoActivo(): Prestamo | null {
    return this.ultimosPrestamos.find(p => !p.fechaDevolucion) || null;
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
