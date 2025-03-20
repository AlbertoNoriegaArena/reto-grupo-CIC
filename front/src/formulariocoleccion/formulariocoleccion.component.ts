import { Component } from '@angular/core';
import { Coleccion } from '../coleccion';
import { FormsModule } from '@angular/forms';
import { ColeccionRestService } from '../coleccion-rest-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-formulario-coleccion',
  imports: [FormsModule],
  templateUrl: './formulariocoleccion.component.html',
  styleUrl: './formulariocoleccion.component.scss'
})
export class FormulariocoleccionComponent {

  coleccion: Coleccion = {} as Coleccion;
  constructor(private coleccionRestService: ColeccionRestService, private router: Router) {}

  public insertar() {
    this.coleccionRestService.insertar(this.coleccion).subscribe(
      (datos: Coleccion) => {
        console.log(datos);
    
      },
      (error) => {
        console.error('Error inserting coleccion:', error);
      }
    );
  }
}