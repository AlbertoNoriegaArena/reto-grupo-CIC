import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Persona } from '../../persona';
import { PersonaService } from '../../persona.service';

@Component({
    selector: 'app-formulariopersonas',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './formulariopersonas.component.html',
    styleUrl: './formulariopersonas.component.scss'
})
export class FormulariopersonasComponent implements OnInit {
    erroresBackend: any = {};

      @Output() formSubmitted = new EventEmitter<void>();
      @Output() formClosed = new EventEmitter<void>();
    
      @Input() isEditMode: boolean = false;
      @Input() persona!: Persona;

    constructor(private personaService: PersonaService) { }

    ngOnInit() {
    
    }

    onSubmit() {
        if (this.isEditMode) {
          this.update();
        } else {
          this.create();
        }
      }
    
      create() {
        this.personaService.insertar(this.persona).subscribe({
          next: (respuesta) => {
            console.log('Usuario guardado con éxito', respuesta);
            this.resetForm();  
            this.formSubmitted.emit();  
            this.formClosed.emit();  
          },
          error: (error) => {
            this.handleBackendErrors(error);
          }
        });
      }
    
      update() {
        this.personaService.updatePersona(this.persona).subscribe({
          next: (respuesta) => {
            console.log('Usuario actualizado con éxito', respuesta);
            this.resetForm();  
            this.formSubmitted.emit();  
            this.formClosed.emit();  
          },
          error: (error) => {
            this.handleBackendErrors(error);
          }
        });
      }
    
      // Función reutilizable para manejar errores del backend
      handleBackendErrors(error: any) {
        if (error.status === 400 && error.error?.errors) {
          this.erroresBackend = error.error.errors.reduce((acc: any, curr: any) => {
            acc[curr.field] = curr.defaultMessage; // Asegura que usas `defaultMessage`
            return acc;
          }, {});
        } else {
          this.erroresBackend = { };
        }
      }
    
      closeForm() {
        this.resetForm();
        this.formClosed.emit();
      }
    
      resetForm() {
        this.persona = {} as Persona;
        this.erroresBackend = {};  // Limpiar los errores
      }
}
