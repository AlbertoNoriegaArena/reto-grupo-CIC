import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FormularioordenadorComponent } from './formulariocoleccion/formulariocoleccion.component';
import { ListaordenadorComponent } from './listacoleccion/listacoleccion.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,ListacoleccionComponent, FormulariocoleccionComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  title = 'ColeccionPersonal';
}
