import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { ItemService } from '../../services/item.service';
import { Item } from '../../models/item';
import { Router } from '@angular/router';
import { TablaGenericaComponent } from '../tabla-generica/tabla-generica.component';
import { CommonModule } from '@angular/common';
import { FormularioItemsComponent } from '../formularioitems/formularioitems.component';
import { ModalComponent } from '../modal/modal.component';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-listaitems',
  standalone: true,
  imports: [
    TablaGenericaComponent,
    CommonModule,
    FormularioItemsComponent,
    ModalComponent,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    FormsModule,
  ],
  templateUrl: './listaitems.component.html',
  styleUrl: './listaitems.component.scss'
})
export class ListaitemsComponent implements OnInit {

  items: Item[] = [];
  dataSource: MatTableDataSource<Item & { tipoNombre?: string; formatoNombre?: string }> = new MatTableDataSource();
  isEditMode = false;
  isModalOpen = false;
  titleModal = 'Agregar Música';

  selectedItem: Item = {formato: {}, tipo: {}} as Item;

  displayedColumns: string[] = ['nombre', 'tipoNombre', 'ubicacion', 'formatoNombre', 'estado', 'acciones'];

  columnDefinitions = [
    { key: 'nombre', label: 'Nombre' },
    { key: 'tipoNombre', label: 'Tipo' },
    { key: 'ubicacion', label: 'Ubicación' },
    { key: 'formatoNombre', label: 'Formato' },
    { key: 'estado', label: 'Estado' },
  ];

  constructor(private itemService: ItemService, private router: Router) { }

  ngOnInit(): void {
    this.loadItems();
  }

  loadItems() {
    this.itemService.getItems().subscribe({
      next: (items) => {
        // Transforma los datos aquí
        const itemsParaTabla = items.map(item => {
          let tipoNombre = 'N/A'; // Por defecto
  
          // Aquí podemos cambiar el tipo por uno más legible en la tabla
          switch (item.tipo?.nombre?.toLowerCase()) {
            case 'musica':
              tipoNombre = 'Música';
              break;
            case 'libro':
              tipoNombre = 'Libro';
              break;
            case 'pelicula':
            case 'película':
              tipoNombre = 'Película';
              break;
            default:
              tipoNombre = item.tipo?.nombre ?? 'N/A';  // Si no hay tipo, mostramos N/A
          }
  
          return {
            ...item,
            tipoNombre: tipoNombre, // Añadimos el nombre transformado
            formatoNombre: item.formato?.nombre ,  
          };
        });
  
        this.items = items; // Puedes mantener la lista original si la necesitas
        this.dataSource.data = itemsParaTabla; // Asigna los datos transformados
      },
      error: (error) => {
        console.error('Error al cargar los items:', error);
        Swal.fire('Error', `No se pudieron cargar los ítems: ${error.message}`, 'error'); // Muestra error al usuario
      }
    });
  }
  

  abrirModalAgregar() {
    
    this.isEditMode = false;
    this.titleModal = 'Agregar artículo';
    this.isModalOpen = true;
  }

  abrirModalEditar(id: number) {
    const item = this.dataSource.data.find(i => i.id === id);
    if (item) {
      this.selectedItem = { ...item };
      this.isEditMode = true;
      this.titleModal = 'Editar artículo';
      this.isModalOpen = true;
    }
  }

  cerrarModal() {
    this.isModalOpen = false;
    this.titleModal = 'Agregar artículo'; //Resetear el título
  }

  onFormSubmitted() {
    this.cerrarModal();
    this.loadItems();
  }

  goToMainPage() {
    this.router.navigate(['/']);
  }

  verDetalles(id: number) {
    const item = this.dataSource.data.find(i => i.id === id);
    if (item) {
      switch (item.tipo?.nombre?.toLowerCase()) {
        case 'libro':
          this.router.navigate(['/detallelibro', id]);
          break;
        case 'pelicula':
        case 'película': // Considera acentos
          this.router.navigate(['/detallepelicula', id]);
          break;
        case 'musica':
        case 'música': // Considera acentos
          this.router.navigate(['/detallemusica', id]);
          break;
        default:
          Swal.fire('No hay vista de detalle específica para este tipo de ítem.');
          break;
      }
    }
  }

  delete(id: number) {
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
        this.itemService.borrar(id).subscribe({
          next: () => {
            Swal.fire('Eliminado', 'Artículo eliminada correctamente');
            this.loadItems();
          },
          error: (error) => Swal.fire('Error', error.message),
        });
      }
    });
  }

  
}
