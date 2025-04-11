// src/app/vistaprincipal/vistaprincipal.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
    selector: 'app-vistaprincipal',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './vistaprincipal.component.html',
    styleUrl: './vistaprincipal.component.scss'
})
export class VistaprincipalComponent {

    constructor(private router: Router) { }

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