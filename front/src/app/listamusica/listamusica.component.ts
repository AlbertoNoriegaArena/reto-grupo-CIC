// src/app/listamusica/listamusica.component.ts
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MusicaService } from '../../musica.service';
import { Musica } from '../../musica';
import { Router } from '@angular/router';
import { TablaGenericaComponent } from '../components/tabla-generica/tabla-generica.component';
import { CommonModule } from '@angular/common';
import { FormulariomusicaComponent } from '../formulariomusica/formulariomusica.component';
import { ModalComponent } from '../components/modal/modal.component';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-listamusica',
    standalone: true,
    imports: [
        TablaGenericaComponent,
        CommonModule,
        FormulariomusicaComponent,
        ModalComponent,
        MatTableModule,
        MatButtonModule,
        MatIconModule,
        FormsModule,
    ],
    templateUrl: './listamusica.component.html',
    styleUrl: './listamusica.component.scss'
})
export class ListamusicaComponent implements OnInit {
    musicas: Musica[] = [];
    dataSource: MatTableDataSource<Musica> = new MatTableDataSource();
    isEditMode = false;
    isModalOpen = false;
    titleModal = 'Agregar Música';

    selectedMusica: Musica = { item: { formato: {} } } as Musica;

    displayedColumns: string[] = ['nombre', 'formato', 'cantante', 'album', 'duracion', 'acciones'];

    columnDefinitions = [
        { key: 'nombre', label: 'Nombre' },
        { key: 'formato', label: 'Formato' },
        { key: 'cantante', label: 'Cantante' },
        { key: 'album', label: 'Album' },
        { key: 'duracion', label: 'Duración' },
        
      ];

    constructor(private musicaService: MusicaService, private router: Router) { }

    ngOnInit(): void {
        this.loadMusica();
    }

    loadMusica() {
        this.musicaService.getMusica().subscribe({
            next: (musicas) => {
                this.musicas = musicas.map(m => ({
                    ...m,
                    nombre: m.item.nombre,
                    formato: m.item.formato.nombre,
                }));
                this.dataSource.data = this.musicas;
            },
            error: (error) => {
                console.error('Error al cargar la música:', error);
            }
        });
    }
    
    abrirModalAgregar() {
    
        this.isEditMode = false;
        this.titleModal = 'Agregar Música'; //Cambiar el título
        this.isModalOpen = true;
      }
    
      abrirModalEditar(id: number) {
        const musica = this.dataSource.data.find(m => m.item.id === id);
        if (musica) {
          this.selectedMusica = { ...musica }; 
          this.isEditMode = true;
          this.titleModal = 'Editar Música'; //Cambiar el título
          this.isModalOpen = true;
        }
      }
    
      cerrarModal() {
        this.isModalOpen = false;
        this.titleModal = 'Agregar Música'; //Resetear el título
      }
    
      onFormSubmitted() {
        this.cerrarModal();
        this.loadMusica(); 
      }
    
      goToMainPage() {
        this.router.navigate(['/']);
      }

      verDetalles(id: number) {
          this.router.navigate(['/detallemusica', id]); //Navegar a la página de detalles
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
              this.musicaService.borrar(id).subscribe(response => {
                if (response.success) {
                  Swal.fire('Eliminado', response.message);
                  this.refreshData();
                } else {
                  Swal.fire('Error', response.message);
                }
              }, error => {
                Swal.fire('Error', error.message, 'error');
              });
            }
          });
        }
      
        refreshData() {
          this.musicaService.getMusica().subscribe(data => {
            this.dataSource.data = data.map(pelicula => ({
              ...pelicula,
              nombre: pelicula.item?.nombre,
              formato: pelicula.item?.formato?.nombre
            }));
          });
        }
    
}
