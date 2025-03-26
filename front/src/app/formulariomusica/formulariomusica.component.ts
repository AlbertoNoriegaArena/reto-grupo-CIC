// src/app/formulariomusica/formulariomusica.component.ts
import { Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Musica } from '../../musica';
import { MusicaService } from '../../musica.service';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-formulariomusica',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './formulariomusica.component.html',
    styleUrl: './formulariomusica.component.scss'
})
export class FormulariomusicaComponent implements OnInit {
    formatos: { nombre: string; id: number }[] = [];
    musica = {item: {formato: {}}} as Musica;
    tipoItemSeleccionado: string = "Musica";
   
    constructor(private musicaService: MusicaService, private router: Router, private http: HttpClient) { }

    onSubmit() {
        console.log('Formulario enviado:', this.musica);
        this.musicaService.insertar(this.musica).subscribe({
            next: (musica) => {
                console.log('Musica insertada:', musica);
              
                this.router.navigate(['/listamusica']);
            },
            error: (error) => {
                console.error('Error al insertar la musica:', error);
            }
        });
    }
    goToListamusica() { // Updated method name
        this.router.navigate(['/listamusica']); // Navigate to listamusica
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
    

