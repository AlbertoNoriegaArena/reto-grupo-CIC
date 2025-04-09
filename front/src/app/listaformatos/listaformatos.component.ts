// src/app/components/formato/formato.component.ts
import { Component, OnInit } from '@angular/core';
import { FormatoService } from '../../formato.service';
import { Formato } from '../../formato';
import { CommonModule } from '@angular/common';
import { ModalComponent } from '../components/modal/modal.component';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';
import { FormularioformatosComponent } from '../formularioformatos/formularioformatos.component';

@Component({
  selector: 'app-formato',
  standalone: true,
  imports: [CommonModule, FormsModule, ModalComponent, FormularioformatosComponent],
  templateUrl: './listaformatos.component.html',
  styleUrls: ['./listaformatos.component.scss']
})
export class ListaFormatoComponent implements OnInit {
  formato: Formato = { id: 0, nombre: '' };
  formatos: Formato[] = [];

  tiposDeItem: { tipoItemNombre: string, formatos: any[] }[] = [];

  selectedFormato: Formato = {} as Formato;
  isModalOpen = false;
  titleModal = 'Agregar Formato'; 


  constructor(private formatoService: FormatoService) { }

  ngOnInit(): void {
    this.loadFormatosAgrupados();
  }

  crearFormato(): void {
    // Llamar al servicio para crear un nuevo formato
    this.formatoService.insertar(this.formato).subscribe({
      next: (nuevoFormato) => {
        this.formatos.push(nuevoFormato);  // Agregar el nuevo formato a la lista
        this.formato = { id: 0, nombre: '' };  // Limpiar el formulario
      },
      error: (err) => {
        console.error('Error al crear el formato', err);
      },
    });
  }

  loadFormatosAgrupados(): void {
    this.formatoService.getFormatosAgrupadosPorTipo().subscribe({
      next: (data) => {
        // Agrupar los formatos por tipo de ítem
        this.tiposDeItem = this.agruparPorTipo(data);
      },
      error: (err) => {
        console.error('Error al cargar los formatos', err);
      }
    });
  }

  // Método para agrupar los formatos por tipo
  agruparPorTipo(data: any[]): { tipoItemNombre: string, formatos: any[] }[] {
    const tiposAgrupados: { tipoItemNombre: string, formatos: any[] }[] = [];

    data.forEach(item => {
      // Verificar si ya existe el tipo en el array
      const tipoExistente = tiposAgrupados.find(tipo => tipo.tipoItemNombre === item.tipoItem.nombre);

      if (tipoExistente) {
        // Si existe, añadir el formato a ese tipo
        tipoExistente.formatos.push(item.formato);
      } else {
        // Si no existe, crear un nuevo grupo para ese tipo
        tiposAgrupados.push({
          tipoItemNombre: item.tipoItem.nombre,
          formatos: [item.formato]
        });
      }
    });

    return tiposAgrupados;
  }

  abrirModalAgregar() {
    this.isModalOpen = true;
  }

  cerrarModal() {
    this.isModalOpen = false;
  }

  onFormSubmitted() {
    this.cerrarModal();
    this.loadFormatosAgrupados();
  }

  delete(id: number){
    Swal.fire({
          title: '¿Estás seguro?',
          text: 'Este registro será eliminado y no lo podrás recuperar',
          showCancelButton: true,
          confirmButtonText: 'Eliminar',
          confirmButtonColor: '#d33',
          cancelButtonText: 'Cancelar',
          reverseButtons: false
        }).then((result) => {
          if (result.isConfirmed) {
            this.formatoService.borrar(id).subscribe({
              next: () => {
                Swal.fire('Eliminado', 'Formato eliminado correctamente');
                this.loadFormatosAgrupados();
              },
              error: (error) => Swal.fire('Error', error.message),
            });
          }
        });
  }
}
