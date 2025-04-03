import { Component, OnInit } from '@angular/core';
import { ActivatedRoute , Router } from '@angular/router';
import { PrestamoService } from '../../prestamo.service';
import { Prestamo } from '../../prestamo'; 
import { CommonModule } from '@angular/common';  

@Component({
  selector: 'app-detalle-prestamo',
  standalone: true,
  imports: [
    CommonModule,  
  ],
  templateUrl: './detalle-prestamo.component.html',
  styleUrl: './detalle-prestamo.component.scss'
})
export class DetallePrestamoComponent implements OnInit{

  prestamo: Prestamo | undefined;
  
    constructor(
      private route: ActivatedRoute,
      private prestamoService: PrestamoService,
      private router: Router,
    ) {}
  
    ngOnInit() {
      const id = Number(this.route.snapshot.paramMap.get('id')); 
      if (id) {
        this.prestamoService.getPrestamoById(id).subscribe((prestamo) => {
          this.prestamo = prestamo; 
        });
      }
    }
  
    goToMainPage() {
      this.router.navigate(['/']);
    }
  
    goToPrestamos() {
      this.router.navigate(['/listaprestamos']);
    }
}
