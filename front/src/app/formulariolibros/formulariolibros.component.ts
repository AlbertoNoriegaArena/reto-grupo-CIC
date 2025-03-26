// src/app/formulariolibros/formulariolibros.component.ts
import { Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Libro } from '../../libro';
import { LibroService } from '../../libro.service';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-formulariolibros',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './formulariolibros.component.html',
    styleUrl: './formulariolibros.component.scss'
})
export class FormulariolibrosComponent implements OnInit {
    formatos: { nombre: string; id: number }[] = [];
    libro = {item: {formato: {}}} as Libro;
    tipoItemSeleccionado: string = "Libro";

    constructor(private libroService: LibroService, private router: Router, private http: HttpClient) { }

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
