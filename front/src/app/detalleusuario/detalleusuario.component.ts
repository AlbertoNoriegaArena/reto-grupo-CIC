import { NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Persona } from '../../persona';
import { ActivatedRoute } from '@angular/router'; 
import { PersonaService } from '../../persona.service';
import { PrestamoService } from '../../prestamo.service'; // Importar PrestamoService

@Component({
  selector: 'app-detalleusuario',
  imports: [NgFor, NgIf],
  templateUrl: './detalleusuario.component.html',
  styleUrl: './detalleusuario.component.scss',
  standalone: true, 
})
export class DetalleUsuarioComponent implements OnInit {

  persona: Persona = {} as Persona; 
  prestamos: any[] = [];
  todosPrestamos: any[] = [];
  mostrarTabla: boolean = false;
  verTodosTabla: boolean = false;
  tipoRepositorio: string = '';

  constructor(
    private route: ActivatedRoute, 
    private personaService: PersonaService,
    private prestamoService: PrestamoService // Inyectamos PrestamoService
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id'); 
    if (id) {
      this.personaService.getPersonaById(Number(id)).subscribe(persona => {
        this.persona = persona;
      });
    }
  }

  // 🔹 Modificado para obtener los préstamos desde el backend
  verPrestamos(tipo: string): void {
    if (!this.persona.id) return; // Asegurar que la persona tiene ID

    this.prestamoService.getPrestamosPorPersona(this.persona.id).subscribe((prestamos: any[]) => {
      this.tipoRepositorio = tipo;
      this.prestamos = prestamos.filter(p => p.item.tipo.nombre.toLowerCase() === tipo.toLowerCase());
      this.mostrarTabla = true;
      this.verTodosTabla = false;
    });
  }

  // 🔹 Obtener TODOS los préstamos del usuario desde el backend
  verTodosPrestamos(): void {
    if (!this.persona.id) return;

    this.prestamoService.getPrestamosPorPersona(this.persona.id).subscribe((prestamos: any[]) => {
      this.todosPrestamos = prestamos;
      this.verTodosTabla = true;
      this.mostrarTabla = false;
    });
  }

  verDetallePrestamo(prestamo: any): void {
    console.log('Ver detalle del préstamo', prestamo);
    // Aquí puedes agregar la lógica para mostrar un modal o redirigir a otra página
  }

  volver(): void {
    this.mostrarTabla = false;
    this.verTodosTabla = false;
  }
}
