import { Component } from '@angular/core';
import { Coleccion } from '../coleccion';
import { ColeccionRestService } from '../coleccion-rest-service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-listacoleccion',
  imports: [RouterLink],
  templateUrl: './listacoleccion.component.html',
  styleUrl: './listacoleccion.component.scss'
})
export class ListacoleccionComponent {

  listaColeccion:Coleccion[]=[];

  constructor(private coleccionRestService:ColeccionRestService, private router:Router) {

    coleccionRestService.buscarTodos().subscribe( (datos)=> {
     
      this.listaColeccion=datos;
    })

  }
}
