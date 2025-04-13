import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Router } from '@angular/router';
import { Persona } from '../../models/persona';
import { PersonaService } from '../../services/persona.service';
import { TablaGenericaComponent } from '../tabla-generica/tabla-generica.component';
import { FormulariopersonasComponent } from '../formulariopersonas/formulariopersonas.component';
import { ModalComponent } from '../modal/modal.component';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-listapersonas',
  standalone: true,
  imports: [
    TablaGenericaComponent,
    CommonModule,
    FormulariopersonasComponent,
    ModalComponent,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    FormsModule,
  ],
  templateUrl: './listapersonas.component.html',
  styleUrl: './listapersonas.component.scss',
})

export class ListapersonasComponent implements OnInit {
  personas: Persona[] = [];

  dataSource: MatTableDataSource<Persona> = new MatTableDataSource();
    isEditMode = false;
    isModalOpen = false;
    titleModal = 'Crear Usuario'; 
  
    selectedPersona: Persona = {} as Persona;
  
    displayedColumns: string[] = ['nombre', 'direccion', 'email', 'telefono', 'acciones'];
  
    columnDefinitions = [
      { key: 'nombre', label: 'Nombre' },
      { key: 'direccion', label: 'Dirección' },
      { key: 'email', label: 'Email' },
      { key: 'telefono', label: 'Teléfono' },
    ];
  constructor(private router: Router, private personaService: PersonaService) { }

  ngOnInit(): void {
    this.loadPersonas();
  }

  loadPersonas(): void {
    this.personaService.getAllPersonas().subscribe(
      (personas) => {
        this.personas = personas;
        this.dataSource.data = personas;
        console.log('Personas cargadas:', this.personas);
      },
      (error) => {
        console.error('Error loading personas:', error);
      }
    );
  }

  abrirModalAgregar() {
    this.isEditMode = false;
    this.titleModal = 'Crear usuario'; 
    this.isModalOpen = true;
  }

  abrirModalEditar(id: number) {
    const persona = this.dataSource.data.find(p => p.id === id);
    if (persona) {
      this.selectedPersona = { ...persona }; 
      this.isEditMode = true;
      this.titleModal = 'Editar usuario'; 
      this.isModalOpen = true;
    }
  }

  cerrarModal() {
    this.isModalOpen = false;
    this.titleModal = 'Crear usuario'; 
  }

  onFormSubmitted() {
    this.cerrarModal();
    this.loadPersonas(); 
  }

  goToMainPage() {
    this.router.navigate(['/']);
  }

  verDetalles(id: number) {
      this.router.navigate(['/detalleusuario', id]); 
    }
  
    delete(id: number) {
      Swal.fire({
        title: '¿Estás seguro?',
        text: 'Este registro será eliminado y no lo podrás recuperar',
        showCancelButton: true,
        confirmButtonText: 'Eliminar',
        confirmButtonColor: '#d33', 
        cancelButtonText: 'Cancelar',
      }).then((result) => {
        if (result.isConfirmed) {
          this.personaService.deletePersona(id).subscribe({
            next: () => {
              Swal.fire('Eliminado', 'Usuario eliminado correctamente');
              this.loadPersonas();
            },
            error: (error) => Swal.fire('Error', error.message),
          });
        }
      });
    }
}
