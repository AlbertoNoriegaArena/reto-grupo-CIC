// src/app/vistaprincipal/vistaprincipal.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { VistaprincipalService } from '../../services/vistaprincipal.service';

@Component({
    selector: 'app-vistaprincipal',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './vistaprincipal.component.html',
    styleUrl: './vistaprincipal.component.scss'
})
export class VistaprincipalComponent {

    totalItems = 0;
    totalLibros = 0;
    totalPeliculas = 0;
    totalMusica = 0;
    totalUsuarios = 0;
    prestamosActivos = 0;
    totalFormatos=0;

    constructor(private router: Router, private VistaprincipalService: VistaprincipalService) { }

    ngOnInit(): void {
        this.VistaprincipalService.getItems().subscribe(data => this.totalItems = data.length);
        this.VistaprincipalService.getLibros().subscribe(data => this.totalLibros = data.length);
        this.VistaprincipalService.getPeliculas().subscribe(data => this.totalPeliculas = data.length);
        this.VistaprincipalService.getMusica().subscribe(data => this.totalMusica = data.length);
        this.VistaprincipalService.getUsuarios().subscribe(data => this.totalUsuarios = data.length);
        this.VistaprincipalService.getPrestamos().subscribe(data => {
            this.prestamosActivos = data.filter(p => 
              p.item?.estado === 'PRESTADO' && !p.borrado
            ).length;
          });
        this.VistaprincipalService.getFormatos().subscribe(data => this.totalFormatos = data.length);
      }

    goToListaItems() {
        this.router.navigate(['/listaitems']);
    }

    goToListaLibros() {
        this.router.navigate(['/listalibros']);
    }
    goToListaPeliculas() {
        this.router.navigate(['/listapeliculas']);
    }
    goToListaMusica() {
        this.router.navigate(['/listamusica']);
    }
    // Navegación a la lista de usuarios
    goToListausuarios() {
        this.router.navigate(['/listausuarios']);  // Asegúrate de tener configurada la ruta '/usuarios'
    }

    // Navegación a la lista de préstamos
    goToPrestamos() {
        this.router.navigate(['/listaprestamos']);  // Asegúrate de tener configurada la ruta '/prestamos'
    }

    goToFormatos() {
        this.router.navigate(['/listaformatos']); 
    }

}