import { CommonModule, NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Persona } from '../../persona';
import { PersonaService } from '../../persona.service';


@Component({
  selector: 'app-listapersonas',
  imports: [CommonModule],
  templateUrl: './listapersonas.component.html',
  styleUrl: './listapersonas.component.scss',
  standalone: true
})

export class ListapersonasComponent implements OnInit {

  personas: Persona[] = [];
  constructor(private router: Router, private personaService: PersonaService) { }

  ngOnInit(): void {
    this.loadPersonas();
  }

  loadPersonas(): void {
    this.personaService.getAllPersonas().subscribe(
      (personas) => {
        this.personas = personas;
      },
      (error) => {
        console.error('Error loading personas:', error);
      }
    );
  }

  verPersona(persona: Persona): void {
    console.log('Ver persona:', persona);
    this.router.navigate(['/detalleusuario', persona.id]); // Navigate to detallecoleccion with id
  }

  editarPersona(persona: Persona): void {
    console.log('Editar persona:', persona);
    this.router.navigate(['/editarpersona', persona.id]); // Changed to number
  }

  eliminarPersona(persona: Persona): void {
    this.personaService.deletePersona(persona.id).subscribe({
      next: () => {
        console.log('Persona eliminada:', persona);
        this.loadPersonas(); // Reload the list after deletion
      },
    });

  }

  goToMainPage() {
    this.router.navigate(['/']);
  }
  goToCreatePage() {
    this.router.navigate(['/crearpersona']);
  }
  goToFormulariopersonas() {
    this.router.navigate(['/formulariopersonas']);
  }

}
