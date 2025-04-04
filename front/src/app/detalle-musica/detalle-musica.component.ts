import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MusicaService } from '../../musica.service';
import { Musica } from '../../musica';
import { CommonModule } from '@angular/common';
import { ModalComponent } from '../components/modal/modal.component';
import { FormulariomusicaComponent } from '../formulariomusica/formulariomusica.component';

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

  constructor(
    private route: ActivatedRoute,
    private musicaService: MusicaService,
    private router: Router,
  ) { }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.musicaService.buscarUno(id).subscribe((musica) => {
        this.musica = musica;
      });
    }
  }

  goToMainPage() {
    this.router.navigate(['/']);
  }

  goToMusica() {
    this.router.navigate(['/listamusica']);
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
