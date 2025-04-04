import { Component, OnInit } from '@angular/core';
import { ActivatedRoute , Router } from '@angular/router';
import { PeliculaService } from '../../pelicula.service';
import { Pelicula } from '../../pelicula'; 
import { CommonModule } from '@angular/common';  
import { ModalComponent } from '../components/modal/modal.component';
import { FormulariopeliculasComponent } from '../formulariopeliculas/formulariopeliculas.component';

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

  constructor(
    private route: ActivatedRoute,
    private peliculaService: PeliculaService,
    private router: Router,
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id')); 
    if (id) {
      this.peliculaService.buscarUno(id).subscribe((pelicula) => {
        this.pelicula = pelicula; 
      });
    }
  }

  goToMainPage() {
    this.router.navigate(['/']);
  }

  goToPeliculas() {
    this.router.navigate(['/listapeliculas']);
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
