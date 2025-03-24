// src/app/formulariomusica/formulariomusica.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Musica } from '../../musica';
import { MusicaService } from '../../musica.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-formulariomusica',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './formulariomusica.component.html',
    styleUrl: './formulariomusica.component.scss'
})
export class FormulariomusicaComponent {
    musica: Musica = {
        id: 0,
        nombrePersona: '',
        albumIngresado: '',
        fechaRecogida: '',
        fechaDevolucion: '',
        genero: '',
        cantante: '',
        album: '',
        duracion: 0
    };

    constructor(private musicaService: MusicaService, private router: Router) { }

    onSubmit() {
        console.log('Formulario enviado:', this.musica);
        this.musicaService.insertar(this.musica).subscribe({
            next: (musica) => {
                console.log('Musica insertada:', musica);
                this.musica = {
                    id: 0,
                    nombrePersona: '',
                    albumIngresado: '',
                    fechaRecogida: '',
                    fechaDevolucion: '',
                    genero: '',
                    cantante: '',
                    album: '',
                    duracion: 0
                };
                this.router.navigate(['/listamusica']);
            },
            error: (error) => {
                console.error('Error al insertar la musica:', error);
            }
        });
    }
    goToMainPage() {
        this.router.navigate(['/']);
    }
}
