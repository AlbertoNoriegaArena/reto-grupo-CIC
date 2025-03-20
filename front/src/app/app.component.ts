import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ListacoleccionComponent } from '../listacoleccion/listacoleccion.component';
import { FormulariocoleccionComponent } from '../formulariocoleccion/formulariocoleccion.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,ListacoleccionComponent, FormulariocoleccionComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {

  title = 'ColeccionPersonal';
}
