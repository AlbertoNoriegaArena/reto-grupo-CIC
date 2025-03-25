import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Persona } from '../../persona';
import { PersonaService } from '../../persona.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-formulariopersonas',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './formulariopersonas.component.html',
    styleUrl: './formulariopersonas.component.scss'
})
export class FormulariopersonasComponent {
    persona = {} as Persona;

    constructor(private personaService: PersonaService, private router: Router) { }

    onSubmit() {
        console.log('Formulario enviado:', this.persona);
        this.personaService.insertar(this.persona).subscribe({
            next: (persona) => {
           
                this.router.navigate(['/listausuarios']);
            },
            error: (error) => {
                console.error('Error al insertar la persona:', error);
            }
        });
    }
    goToListausuarios() {
        this.router.navigate(['/listausuarios']);
    }
}
