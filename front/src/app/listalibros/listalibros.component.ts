// src/app/listalibros/listalibros.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LibroService } from '../../libro.service';
import { Libro } from '../../libro';
import { Router } from '@angular/router';

@Component({
    selector: 'app-listalibros',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './listalibros.component.html',
    styleUrl: './listalibros.component.scss'
})
export class ListalibrosComponent implements OnInit {
    libros: Libro[] = [];

    constructor(private libroService: LibroService, private router: Router) { }

    ngOnInit(): void {
        this.loadLibros();
    }

    loadLibros() {
        this.libroService.getLibros().subscribe({
            next: (libros) => {
                this.libros = libros;
            },
            error: (error) => {
                console.error('Error al cargar los libros:', error);
            }
        });
    }
    goToFormularioLibros() {
        this.router.navigate(['/formulariolibros']);
    }
    goToMainPage() {
        this.router.navigate(['/']);
    }
}
