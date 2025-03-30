import { Component, OnInit } from '@angular/core';
import { ActivatedRoute , Router } from '@angular/router';
import { PeliculaService } from '../../pelicula.service';
import { Pelicula } from '../../pelicula'; 
import { CommonModule } from '@angular/common';  

@Component({
  selector: 'app-detalle-pelicula',
  standalone: true,
  imports: [
    CommonModule,  
  ],
  templateUrl: './detalle-pelicula.component.html',
  styleUrls: ['./detalle-pelicula.component.scss'],
})
export class DetallePeliculaComponent implements OnInit {
  pelicula: Pelicula | undefined;

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
}
