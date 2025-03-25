import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { FormulariocoleccionComponent } from './formulariocoleccion/formulariocoleccion.component';
import { ListacoleccionComponent } from './listacoleccion/listacoleccion.component';
import { DetallecoleccionComponent } from "./detallecoleccion/detallecoleccion.component";
import { VistaprincipalComponent } from "./vistaprincipal/vistaprincipal.component";
import { DetalleUsuarioComponent } from "./detalleusuario/detalleusuario.component";
import { ListapersonasComponent } from "./listapersonas/listapersonas.component";
import { PrestamosService } from '../prestamo.service';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {

  title = 'ColeccionPersonal';
}
