import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MusicaService } from '../../musica.service';
import { Musica } from '../../musica';
import { CommonModule } from '@angular/common';
import { ModalComponent } from '../components/modal/modal.component';
import { FormulariomusicaComponent } from '../formulariomusica/formulariomusica.component';
import { PrestamoService } from '../../prestamo.service';
import { Prestamo } from '../../prestamo';

@Component({
  selector: 'app-detalle-musica',
  standalone: true,
  imports: [
    CommonModule,
    ModalComponent,
    FormulariomusicaComponent,
  ],
  templateUrl: './detalle-musica.component.html',
  styleUrl: './detalle-musica.component.scss'
})
export class DetalleMusicaComponent implements OnInit {

  musica: Musica | undefined;
  isModalOpen = false;
  titleModal = 'Editar Préstamo';
  isEditMode = true;
  musicaSeleccionada: Musica = { item: { formato: {} } } as Musica;
  prestamos: Prestamo[] = [];

  constructor(
    private route: ActivatedRoute,
    private musicaService: MusicaService,
    private router: Router,
    private prestamoService: PrestamoService,
  ) { }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.musicaService.buscarUno(id).subscribe((musica) => {
        this.musica = musica;
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

  goToMusica() {
    this.router.navigate(['/listamusica']);
  }

  irAlPrestamo(id: number) {
    const prestamo = this.prestamos.find((p) => p.item.id === id);

    if (prestamo) {
      // Si se encuentra el préstamo, redirige al detalle del préstamo
      this.router.navigate([`/detalleprestamo/${prestamo.id}`]);
    } else {
      console.error('No se encontró un préstamo para esta música');
    }
  }

  actualizarRegistro(id: number) {
    if (!this.musica) return;
    this.musicaSeleccionada = { ...this.musica };
    this.isModalOpen = true;
  }

  cerrarModal() {
    this.isModalOpen = false;
  }

  onFormSubmitted() {
    if (this.musicaSeleccionada?.item.id) {
      this.musicaService.buscarUno(this.musicaSeleccionada.item.id).subscribe(
         (musicaActualizada) => {
          this.musica = musicaActualizada;
          this.cerrarModal();
          
        },
        (error) => {
          console.error("Error al actualizar el detalle del préstamo:", error);
        }
      );
    }
  }
}
