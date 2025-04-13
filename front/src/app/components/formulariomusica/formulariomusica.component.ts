// src/app/formulariomusica/formulariomusica.component.ts
import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Musica } from '../../models/musica';
import { MusicaService } from '../../services/musica.service';

@Component({
    selector: 'app-formulariomusica',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './formulariomusica.component.html',
    styleUrl: './formulariomusica.component.scss'
})
export class FormulariomusicaComponent implements OnInit {
    formatos: { nombre: string; id: number }[] = [];
    tipoItemSeleccionado: string = "Musica";
    erroresBackend: any = {};

    @Output() formSubmitted = new EventEmitter<void>();
    @Output() formClosed = new EventEmitter<void>();

    @Input() isEditMode: boolean = false;
    @Input() musica!: Musica;

    constructor(private musicaService: MusicaService,) { }

    ngOnInit() {
        this.getFormatos(); // Llamada a la API para obtener los formatos
    }

    // Llamada al servicio para obtener los formatos
    getFormatos() {
        this.musicaService.getFormatos(this.tipoItemSeleccionado).subscribe({
            next: (data) => {
                this.formatos = data;
            },
            error: (error) => {
                console.error('Error fetching formats:', error);
            }
        });
    }

    onSubmit() {
        if (this.isEditMode) {
            this.update();
        } else {
            this.create();
        }
    }

    create() {
        this.musicaService.insertar(this.musica).subscribe({
            next: (respuesta) => {
                console.log('Música guardada con éxito', respuesta);
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
        this.musicaService.actualizar(this.musica).subscribe({
            next: (respuesta) => {
                console.log('Película actualizada con éxito', respuesta);
                this.erroresBackend = {}; // Limpiar errores si la petición fue exitosa
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
            this.erroresBackend = { general: 'El nombre y el formato son obligatorios' };
        }
    }

    closeForm() {
        this.resetForm();
        this.formClosed.emit();
    }

    resetForm() {
        // Limpiar el objeto 'musica' 
        this.musica = { item: { formato: {} }, } as Musica;
        this.erroresBackend = {};  // Limpiar los errores
    }
}