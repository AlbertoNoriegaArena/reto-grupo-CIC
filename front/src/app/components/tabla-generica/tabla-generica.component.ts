import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule, MatPaginatorIntl } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule, DatePipe } from '@angular/common';
import { CustomPaginator } from '../../custom-paginator';
import { Router } from '@angular/router';
import { PeliculaService } from '../../../pelicula.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-tabla-generica',
  standalone: true,
  imports: [
    CommonModule,
    DatePipe,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule
  ],
  templateUrl: './tabla-generica.component.html',
  styleUrls: ['./tabla-generica.component.scss'],
  providers: [
    { provide: MatPaginatorIntl, useValue: CustomPaginator() } // Aplica el paginator personalizado
  ]
})
export class TablaGenericaComponent implements OnInit {
  @Input() dataSource: MatTableDataSource<any> = new MatTableDataSource();
  @Input() displayedColumns: string[] = [];
  @Input() columnDefinitions: { key: string; label: string }[] = [];
  constructor(private router: Router,
    private peliculaService: PeliculaService,
  ) { }

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit(): void { }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.sort.disableClear = true; // Esto evita que vuelva al estado original
    this.dataSource.paginator = this.paginator;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.dataSource.filter = filterValue;

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  verDetalles(idItem: number) {
    // Navegar a la página de detalles de la película
    this.router.navigate(['/detallepelicula', idItem]);
  }

  update(id: number) {
    // Navegar a la página de edición con el id del elemento
    this.router.navigate(['/formulariopeliculas', id]);
  }

  // Método para confirmar la eliminación usando SweetAlert2
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
        this.peliculaService.borrar(id).subscribe(response => {
          if (response.success) {
            Swal.fire('Eliminado', response.message);
            this.refreshData(); // Refresca la tabla
          } else {
            Swal.fire('Error', response.message); // Muestra el mensaje del backend
          }
        }, error => {
          Swal.fire('Error', error.message, 'error'); // Error genérico
        });
      }
    });
  }

  isDate(value: any): boolean {
    if (typeof value === 'string') {
      // Intenta parsear la cadena como una fecha
      return !isNaN(Date.parse(value));
    }
    return value instanceof Date;
  }

  isNumber(value: any): boolean {
    return typeof value === 'number';
  }

  refreshData() {
    this.peliculaService.getPeliculas().subscribe(data => {
      this.dataSource.data = data.map(pelicula => ({
        ...pelicula,
        nombre: pelicula.item?.nombre,
        formato: pelicula.item?.formato?.nombre
      }));
    });
  }

}
