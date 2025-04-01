import { Component, OnInit } from '@angular/core';
import { ActivatedRoute , Router } from '@angular/router';
import { LibroService } from '../../libro.service';
import { Libro } from '../../libro'; 
import { CommonModule } from '@angular/common'; 

@Component({
  selector: 'app-detalle-libro',
  imports: [
    CommonModule,  
  ],
  templateUrl: './detalle-libro.component.html',
  styleUrl: './detalle-libro.component.scss'
})
export class DetalleLibroComponent implements OnInit {
  libro: Libro | undefined;

  constructor(
    private route: ActivatedRoute,
    private libroService: LibroService,
    private router: Router,
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id')); 
    if (id) {
      this.libroService.buscarUno(id).subscribe((libro) => {
        this.libro = libro; 
      });
    }
  }

  goToMainPage() {
    this.router.navigate(['/']);
  }

  goToLibros() {
    this.router.navigate(['/listalibros']);
  }
}
