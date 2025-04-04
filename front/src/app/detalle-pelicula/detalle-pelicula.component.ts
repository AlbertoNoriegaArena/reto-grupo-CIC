import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PeliculaService } from '../../pelicula.service';
import { Pelicula } from '../../pelicula';
import { CommonModule } from '@angular/common';
import { ModalComponent } from '../components/modal/modal.component';
import { FormulariopeliculasComponent } from '../formulariopeliculas/formulariopeliculas.component';
import { PrestamoService } from '../../prestamo.service';
import { Prestamo } from '../../prestamo';
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

  goToPeliculas() {
    this.router.navigate(['/listapeliculas']);
  }

  irAlPrestamo(id: number) {
    const prestamo = this.prestamos.find((p) => p.item.id === id);

    if (prestamo) {
      // Si se encuentra el préstamo, redirige al detalle del préstamo
      this.router.navigate([`/detalleprestamo/${prestamo.id}`]);
    } else {
      console.error('No se encontró un préstamo para esta pelicula');
    }
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
