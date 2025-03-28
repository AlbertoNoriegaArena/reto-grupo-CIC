// src/app/listamusica/listamusica.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MusicaService } from '../../musica.service';
import { Musica } from '../../musica';
import { Router } from '@angular/router';

@Component({
    selector: 'app-listamusica',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './listamusica.component.html',
    styleUrl: './listamusica.component.scss'
})
export class ListamusicaComponent implements OnInit {
    musicas: Musica[] = [];


    constructor(private musicaService: MusicaService, private router: Router) { }

    ngOnInit(): void {
        this.loadMusica();
    }

    loadMusica() {
        this.musicaService.getMusica().subscribe({
            next: (musicas) => {
                this.musicas = musicas;
            },
            error: (error) => {
                console.error('Error al cargar la música:', error);
            }
        });
    }
    goToFormularioMusica() {
        this.router.navigate(['/formulariomusica']);
    }
    goToMainPage() {
        this.router.navigate(['/']);
    }
}
