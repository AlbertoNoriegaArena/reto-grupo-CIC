import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Coleccion } from '../coleccion';
import { ColeccionRestService } from '../coleccion-rest-service';

@Component({
  selector: 'app-detallecoleccion',
  imports: [],
  templateUrl: './detallecoleccion.component.html',
  styleUrl: './detallecoleccion.component.scss'
})
export class DetallecoleccionComponent {

  coleccion: Coleccion = {} as Coleccion;

  constructor(private coleccionRestService: ColeccionRestService, private route: ActivatedRoute) {

  }

  ngOnInit() {

    console.log(this.route.snapshot.paramMap.get("tipo"));
    let tipo=this.route.snapshot.paramMap.get("tipo");
    if (tipo) {
    this.coleccionRestService.buscarUno(tipo).subscribe(); {
      this.coleccion=this.coleccion;
    }
  }
}
}
