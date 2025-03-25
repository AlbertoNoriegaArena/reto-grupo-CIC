import { NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-listausuarios',
  imports: [NgFor, NgIf],
  templateUrl: './listausuarios.component.html',
  styleUrl: './listausuarios.component.scss'
})

export class ListausuariosComponent implements OnInit {
  // Lista de usuarios (simulada)
  usuarios = [
    { id: 1, nombre: 'Juan Pérez', direccion: 'Calle Ficticia 123', email: 'juan.perez@gmail.com', telefono: '555-1234' },
    { id: 2, nombre: 'María González', direccion: 'Avenida Siempre Viva 456', email: 'maria.gonzalez@gmail.com', telefono: '555-5678' },
    // Agrega más usuarios si es necesario
  ];

  constructor() { }

  ngOnInit(): void {
    // Aquí podrías cargar los usuarios desde un servicio si fuera necesario
  }

  // Función para ver detalles del usuario
  verUsuario(usuario: any): void {
    console.log('Ver usuario:', usuario);
    // Lógica para redirigir o mostrar detalles
  }

  // Función para editar un usuario
  editarUsuario(usuario: any): void {
    console.log('Editar usuario:', usuario);
    // Lógica para editar el usuario (puedes redirigir a otro formulario de edición)
  }

  // Función para eliminar un usuario
  eliminarUsuario(usuario: any): void {
    const index = this.usuarios.indexOf(usuario);
    if (index > -1) {
      this.usuarios.splice(index, 1); // Eliminar usuario de la lista
      console.log('Usuario eliminado:', usuario);
    }
  }
}