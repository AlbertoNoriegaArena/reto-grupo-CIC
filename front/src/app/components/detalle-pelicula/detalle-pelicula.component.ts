import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PeliculaService } from '../../services/pelicula.service';
import { Pelicula } from '../../models/pelicula';
import { CommonModule } from '@angular/common';
import { ModalComponent } from '../modal/modal.component';
import { FormulariopeliculasComponent } from '../formulariopeliculas/formulariopeliculas.component';
import { PrestamoService } from '../../services/prestamo.service';
import { Prestamo } from '../../models/prestamo';

@Component({
  selector: 'app-detalle-pelicula',
  standalone: true,
  imports: [
    CommonModule,
    ModalComponent,
    FormulariopeliculasComponent,
  ],
  templateUrl: './detalle-pelicula.component.html',
  styleUrls: ['./detalle-pelicula.component.scss'],
})
export class DetallePeliculaComponent implements OnInit {
  pelicula: Pelicula | undefined;
  isModalOpen = false;
  titleModal = 'Editar película';
  isEditMode = true;
  peliculaSeleccionada: Pelicula = { item: { formato: {} } } as Pelicula;
  prestamos: Prestamo[] = [];
  ultimosPrestamos: Prestamo[] = [];
  prestamoActivo: Prestamo | null = null;

  constructor(
    private route: ActivatedRoute,
    private peliculaService: PeliculaService,
    private router: Router,
    private prestamoService: PrestamoService,
  ) { }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.peliculaService.buscarUno(id).subscribe((pelicula) => {
        this.pelicula = pelicula;
  
        const itemId = pelicula?.item?.id;
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

  goToPeliculas() {
    this.router.navigate(['/listapeliculas']);
  }

  irAlPrestamo(id: number) {
    this.router.navigate([`/detalleprestamo/${id}`]);
  }

  getPrestamoActivo(): Prestamo | null {
    return this.ultimosPrestamos.find(p => !p.fechaDevolucion) || null;
  }

  actualizarRegistro(id: number) {
    if (!this.pelicula) return;
    this.peliculaSeleccionada = { ...this.pelicula };
    this.isModalOpen = true;
  }

  cerrarModal() {
    this.isModalOpen = false;
  }

  onFormSubmitted() {
    if (this.peliculaSeleccionada?.item.id) {
      this.peliculaService.buscarUno(this.peliculaSeleccionada.item.id).subscribe(
        (peliculaActualizada) => {
          this.pelicula = peliculaActualizada;
          this.cerrarModal();

        },
        (error) => {
          console.error("Error al actualizar el detalle del préstamo:", error);
        }
      );
    }
  }
}
