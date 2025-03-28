import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule, MatPaginatorIntl } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule, DatePipe } from '@angular/common';
import { CustomPaginator } from '../../custom-paginator'; // Importa la función

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

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit(): void {}

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
      // Intenta parsear la cadena como una fecha
      return !isNaN(Date.parse(value));
    }
    return value instanceof Date;
  }

  isNumber(value: any): boolean {
    return typeof value === 'number';
  }
}
