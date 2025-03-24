import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { ColeccionService } from '../../coleccion.service';
import { Coleccion } from '../../coleccion';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-formulariocoleccion',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './formulariocoleccion.component.html',
  styleUrl: './formulariocoleccion.component.scss'
})
export class FormulariocoleccionComponent {

  coleccion: Coleccion = {} as Coleccion;
  constructor(private coleccionService: ColeccionService, private router: Router) {}

  public insertar() {
    this.coleccionService.insertar(this.coleccion).subscribe(
      (datos: Coleccion) => {
        console.log(datos);
    
      },
      (error) => {
        console.error('Error inserting coleccion:', error);
      }
    );
  }
}