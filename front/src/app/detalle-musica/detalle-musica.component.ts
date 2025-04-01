import { Component, OnInit } from '@angular/core';
import { ActivatedRoute , Router } from '@angular/router';
import { MusicaService } from '../../musica.service';
import { Musica } from '../../musica'; 
import { CommonModule } from '@angular/common';  

@Component({
  selector: 'app-detalle-musica',
  standalone: true,
  imports: [
    CommonModule,  
  ],
  templateUrl: './detalle-musica.component.html',
  styleUrl: './detalle-musica.component.scss'
})
export class DetalleMusicaComponent implements OnInit{

   musica: Musica | undefined;

    constructor(
       private route: ActivatedRoute,
       private musicaService: MusicaService,
       private router: Router,
     ) {}

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
}
