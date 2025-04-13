import { NgFor, NgIf, DatePipe  } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Persona } from '../../models/persona';
import { ActivatedRoute, Router } from '@angular/router';
import { PersonaService } from '../../services/persona.service';
import { PrestamoService } from '../../services/prestamo.service'; // Importar PrestamoService
import { ModalComponent } from '../../components/modal/modal.component';
import { FormulariopersonasComponent } from '../formulariopersonas/formulariopersonas.component';

@Component({
  selector: 'app-detalleusuario',
  imports: [NgFor, NgIf, DatePipe, FormulariopersonasComponent, ModalComponent],
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
  personaSeleccionada: Persona = {} as Persona;
  isModalOpen = false;
  titleModal = 'Editar película';
  isEditMode = true;

  prestamosAsociados: 'todos' | 'pelicula' | 'musica' | 'libro' = 'todos';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private personaService: PersonaService,
    private prestamoService: PrestamoService // Inyectamos PrestamoService
  ) { }

  ngOnInit(): void {

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.personaService.getPersonaById(Number(id)).subscribe(persona => {
        this.persona = persona;
        this.verTodosPrestamos();
      });
    }

  }

  verPrestamos(tipo: string): void {
    if (!this.persona.id) return;

    // Evita recargar si ya está mostrando ese tipo
    if (this.prestamosAsociados === tipo) return;

    this.prestamosAsociados = tipo as 'pelicula' | 'libro' | 'musica';

    this.prestamoService.getPrestamosPorPersona(this.persona.id).subscribe((prestamos: any[]) => {
      this.tipoRepositorio = tipo;

      // Filtrar por tipo y además que no tengan fechaDevolucion (activos)
      this.prestamos = prestamos.filter(p =>
        p.item.tipo.nombre.toLowerCase() === tipo.toLowerCase() &&
        !p.fechaDevolucion
      );

      this.mostrarTabla = true;
      this.verTodosTabla = false;
    });
  }


  verTodosPrestamos(): void {
    if (!this.persona.id) return;

    if (this.prestamosAsociados === 'todos' && this.todosPrestamos.length > 0) return;

    this.prestamosAsociados = 'todos';
    this.verTodosTabla = true;
    this.mostrarTabla = false;

    this.prestamoService.getPrestamosPorPersona(this.persona.id).subscribe((prestamos: any[]) => {
      // Solo prestamos activos: sin fechaDevolucion
      this.todosPrestamos = prestamos.filter(p => !p.fechaDevolucion);
    });
  }

  verDetallePrestamo(itemId: number) {
    let prestamo;

    if (this.prestamosAsociados === 'todos') {
      prestamo = this.todosPrestamos.find((p) => p.item.id === itemId);
    } else {
      prestamo = this.prestamos.find((p) => p.item.id === itemId);
    }

    if (prestamo) {
      this.router.navigate([`/detalleprestamo/${prestamo.id}`]);
    } else {
      console.error('No se encontró el préstamo');
    }
  }

  actualizarRegistro(id: number) {
    if (!this.persona) return;
    this.personaSeleccionada = { ...this.persona };
    this.isModalOpen = true;
  }

  cerrarModal() {
    this.isModalOpen = false;
  }

  onFormSubmitted() {
    if (this.personaSeleccionada?.id) {
      this.personaService.getPersonaById(this.personaSeleccionada.id).subscribe(
        (personaActualizada) => {
          this.persona = personaActualizada;
          this.cerrarModal();

        },
        (error) => {
          console.error("Error al actualizar el detalle del usuario", error);
        }
      );
    }
  }
  
}
