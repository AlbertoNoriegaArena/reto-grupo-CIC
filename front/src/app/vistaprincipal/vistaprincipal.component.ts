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
    showLibros = false;
    showPeliculas = false;
    showMusica = false;

    constructor(private router: Router) { }

    toggleLibros() {
        this.showLibros = !this.showLibros;
    }

    goToFormularioLibros() {
        this.router.navigate(['/formulariolibros']);
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
}
