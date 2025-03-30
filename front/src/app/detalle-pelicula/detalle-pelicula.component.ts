import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PeliculaService } from '../../pelicula.service';
import { Pelicula } from '../../pelicula'; 
import { CommonModule , DatePipe } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-detalle-pelicula',
  templateUrl: './detalle-pelicula.component.html',
  styleUrls: ['./detalle-pelicula.component.scss'],
  providers: [CommonModule , DatePipe],  
})

export class DetallePeliculaComponent implements OnInit {
  pelicula: Pelicula | undefined;

  constructor(
    private route: ActivatedRoute,
    private peliculaService: PeliculaService,
    private datePipe: DatePipe ,
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
