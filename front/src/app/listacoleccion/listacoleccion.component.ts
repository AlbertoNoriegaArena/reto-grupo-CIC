import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { Coleccion } from '../../coleccion';
import { ColeccionService } from '../../coleccion.service';

@Component({
  selector: 'app-listacoleccion',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './listacoleccion.component.html',
  styleUrl: './listacoleccion.component.scss'
})
export class ListacoleccionComponent implements OnInit {
  listaColeccion: Coleccion[] = [];

  constructor(private coleccionService: ColeccionService) { }

  ngOnInit(): void {
    this.coleccionService.buscarTodos().subscribe(
      (data: Coleccion[]) => {
        this.listaColeccion = data;
      },
      (error: any) => {
        console.error('Error fetching collections:', error);
      }
    );
  }
}