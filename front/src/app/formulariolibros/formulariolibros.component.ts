// src/app/formulariolibros/formulariolibros.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Libro } from '../../libro';
import { LibroService } from '../../libro.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-formulariolibros',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './formulariolibros.component.html',
    styleUrl: './formulariolibros.component.scss'
})
export class FormulariolibrosComponent {
   libro = {} as Libro

    constructor(private libroService: LibroService, private router: Router) { }

    onSubmit() {
        console.log('Formulario enviado:', this.libro);
        this.libroService.insertar(this.libro).subscribe({
            next: (libro) => {
                console.log('Libro insertado:', libro);
             
                this.router.navigate(['/listalibros']);
            },
            error: (error) => {
                console.error('Error al insertar el libro:', error);
            }
        });
    }
    goToListalibros() { // Updated method name
        this.router.navigate(['/listalibros']); // Navigate to listamusica
      }
}
