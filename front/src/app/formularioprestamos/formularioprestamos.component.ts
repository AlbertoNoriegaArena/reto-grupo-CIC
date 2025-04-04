import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Prestamo } from '../../prestamo';
import { PrestamoService } from '../../prestamo.service';
import { PersonaService } from '../../persona.service';
import { Persona } from '../../persona';

@Component({
  selector: 'app-formularioprestamos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './formularioprestamos.component.html',
  styleUrl: './formularioprestamos.component.scss'
})
export class FormularioprestamosComponent implements OnInit {
  personas: Persona[] = [];
  items: { id: number; nombre: string }[] = [];
  erroresBackend: any = {};

  nombreItem: string = '';
  nombrePersona: string = '';

  @Output() formSubmitted = new EventEmitter<void>();
  @Output() formClosed = new EventEmitter<void>();

  @Input() isEditMode: boolean = false;
  @Input() prestamo!: Prestamo;
  @Input() prestamoDevuelto!: EventEmitter<void>;

  constructor(
    private prestamoService: PrestamoService, private personaService: PersonaService) { }

  ngOnInit() {
    this.resetForm();
    this.loadPersonas();
    this.loadItems();
    // Escuchar evento de devolución
    this.prestamoDevuelto?.subscribe(() => this.loadItems());
  }

  loadPersonas() {
    this.personaService.getAllPersonas().subscribe({
      next: (data) => {
        this.personas = data;
      },
      error: (error) => {
        console.error('Error al cargar personas', error);
      }
    });
  }

  loadItems() {
    this.prestamoService.getAvailableItems().subscribe({ 
      next: (data) => {
        this.items = data;
      },
      error: (error) => {
        console.error('Error al cargar ítems disponibles', error);
      }
    });
  }

  onSubmit() {
    if (this.prestamo.fechaDevolucion === "EN PRESTAMO") {
      this.prestamo.fechaDevolucion = null; // Asegurar que se envíe como null
    }

    console.log("Enviando datos:", this.prestamo); // Verifica qué se envía

    if (this.isEditMode) {
      this.update();
    } else {
      this.create();
    }
  }

  create() {
    this.prestamoService.insertar(this.prestamo).subscribe({
      next: (respuesta) => {
        console.log('Prestamo guardada con éxito', respuesta);
        this.loadItems();
        this.resetForm();  // Limpiar el formulario después de guardar
        this.formSubmitted.emit();  // Emitir el evento de éxito
        this.formClosed.emit();  // Emitir el evento de cierre
      },
      error: (error) => {
        this.handleBackendErrors(error);
      }
    });
  }

  update() {
    this.prestamoService.updatePrestamo(this.prestamo).subscribe({
      next: (respuesta) => {
        console.log('Película actualizada con éxito', respuesta);
        this.loadItems();
        this.resetForm();  // Limpiar el formulario después de guardar
        this.formSubmitted.emit();  // Emitir el evento de éxito
        this.formClosed.emit();  // Emitir el evento de cierre
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
      this.erroresBackend = { general: 'La persona y el artículo son obligatorios' };
    }
  }

  closeForm() {
    this.resetForm();
    this.formClosed.emit();
  }

  resetForm() {
    this.prestamo = {
      item: { formato: {} },
      persona: {},

    } as Prestamo;
    this.erroresBackend = {};  // Limpiar los errores
  }

  esEditable(): boolean {
    return this.prestamo.fechaDevolucion !== null && this.prestamo.fechaDevolucion !== "EN PRESTAMO";
  }
}
