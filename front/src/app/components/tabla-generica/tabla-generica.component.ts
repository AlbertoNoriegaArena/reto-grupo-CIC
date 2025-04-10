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
    MatButtonModule,
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

  // Callbacks genéricos para acciones
  @Input() deleteCallback!: (id: number) => void;
  @Input() editCallback!: (id: number) => void;
  @Input() viewDetailsCallback!: (id: number) => void;
  @Input() devolverCallback!: (id: number) => void;
  @Input() tipoTabla!: string;  
  
  constructor(private router: Router,) { }

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

  isDate(value: any): boolean {
    if (typeof value === 'string') {
      // Try parsing with Date.parse() first
      const parsedDate = Date.parse(value);
      if (!isNaN(parsedDate)) {
        // If parsing is successful, check if the original string can be formatted back to a date
        const date = new Date(parsedDate);
        const formattedDate = date.toLocaleDateString('en-CA', { year: 'numeric', month: '2-digit', day: '2-digit' });
        // Check if the formatted date is different from the original string
        if (formattedDate.replace(/-/g, '/') !== value.replace(/-/g, '/')) {
          return false;
        }
        return true;
      }
      return false;
    }
    return value instanceof Date;
  }


  isNumber(value: any): boolean {
    return typeof value === 'number';
  }


  onVerDetalles(idItem: number) {
    if (this.viewDetailsCallback) {
      this.viewDetailsCallback(idItem);  // Llama al callback genérico para ver detalles
    }
  }

  onEdit(id: number) {
    if (this.editCallback) {
      this.editCallback(id);  // Ejecuta el callback genérico para editar
    }
  }

  onDelete(id: number) {
    if (this.deleteCallback) {
      this.deleteCallback(id); // Ejecuta el callback con el ID
    }
  }

  onDevolver(id: number) {
    if (this.devolverCallback) {
      this.devolverCallback(id);  // Ejecuta el callback con el ID
    }
  }

}
