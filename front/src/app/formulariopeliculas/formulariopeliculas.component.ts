// src/app/formulariopeliculas/formulariopeliculas.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Pelicula } from '../../pelicula';
import { PeliculaService } from '../../pelicula.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-formulariopeliculas',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './formulariopeliculas.component.html',
    styleUrl: './formulariopeliculas.component.scss'
})
export class FormulariopeliculasComponent {
   pelicula = {} as Pelicula

    constructor(private peliculaService: PeliculaService, private router: Router) { }

    onSubmit() {
        console.log('Formulario enviado:', this.pelicula);
        this.peliculaService.insertar(this.pelicula).subscribe({
            next: (pelicula) => {
                console.log('Pelicula insertada:', pelicula);
                
                this.router.navigate(['/listapeliculas']);
            },
            error: (error) => {
                console.error('Error al insertar la pelicula:', error);
            }
        });
    }
    goToMainPage() {
        this.router.navigate(['/']);
    }
}
