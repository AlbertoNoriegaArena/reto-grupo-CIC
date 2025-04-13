import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MusicaService } from '../../services/musica.service';
import { Musica } from '../../models/musica';
import { CommonModule } from '@angular/common';
import { ModalComponent } from '../modal/modal.component';
import { FormulariomusicaComponent } from '../formulariomusica/formulariomusica.component';
import { PrestamoService } from '../../services/prestamo.service';
import { Prestamo } from '../../models/prestamo';

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
  ultimosPrestamos: Prestamo[] = [];
  prestamoActivo: Prestamo | null = null;

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
  
        const itemId = musica?.item?.id;
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

  goToMusica() {
    this.router.navigate(['/listamusica']);
  }

  irAlPrestamo(id: number) {
    this.router.navigate([`/detalleprestamo/${id}`]);
  }

  getPrestamoActivo(): Prestamo | null {
    return this.ultimosPrestamos.find(p => !p.fechaDevolucion) || null;
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
