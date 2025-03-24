import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ColeccionService } from '../../coleccion.service';
import { Coleccion } from '../../coleccion';


@Component({
  selector: 'app-detallecoleccion',
  imports: [],
  templateUrl: './detallecoleccion.component.html',
  styleUrl: './detallecoleccion.component.scss'
})
export class DetallecoleccionComponent {

  coleccion: Coleccion = {} as Coleccion;

  constructor(private coleccionService: ColeccionService, private route: ActivatedRoute) {

  }

  ngOnInit() {

    console.log(this.route.snapshot.paramMap.get("tipo"));
    let tipo=this.route.snapshot.paramMap.get("tipo");
    if (tipo) {
    this.coleccionService.buscarUno(tipo).subscribe(); {
      this.coleccion=this.coleccion;
    }
  }
}
}
