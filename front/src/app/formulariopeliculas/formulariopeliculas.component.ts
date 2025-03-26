// src/app/formulariopeliculas/formulariopeliculas.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Pelicula } from '../../pelicula';
import { PeliculaService } from '../../pelicula.service';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-formulariopeliculas',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './formulariopeliculas.component.html',
    styleUrl: './formulariopeliculas.component.scss'
})
export class FormulariopeliculasComponent implements OnInit {
    formatos: { nombre: string; id: number }[] = [];
    tipoItemSeleccionado: string = "Pelicula";
    pelicula = {item: {formato: {}}} as Pelicula;

    constructor(private peliculaService: PeliculaService, private router: Router, private http: HttpClient) { }

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
    goToListapeliculas() { // Updated method name
        this.router.navigate(['/listapeliculas']); // Navigate to listamusica
      }
    
    ngOnInit() {
        this.getFormatos();
    }
    
    getFormatos() {
        this.http.get<any[]>('http://localhost:4200/api/TipoItemFormatos').subscribe(
            (data) => {
                this.formatos = data
                  .filter(item => item.tipoItem.nombre === this.tipoItemSeleccionado)
                  .map(item => ({
                    id: item.formato.id, // Use formato.id as value
                    nombre: item.formato.nombre // Use formato.nombre as display name
                  }));
              },
            (error) => {
                console.error('Error fetching formats:', error);
            }
        );
    }
}
