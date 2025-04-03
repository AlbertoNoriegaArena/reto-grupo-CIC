import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Prestamo } from './prestamo';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PrestamoService {
  private apiUrl = 'http://localhost:4200/api/prestamos'; // Asegúrate de que esta sea la URL de tu backend
  private itemsUrl = 'http://localhost:4200/api/items/disponibles';

  constructor(private http: HttpClient) {}

  // Crear un nuevo préstamo
  insertar(prestamo: Prestamo): Observable<Prestamo> {
    return this.http.post<Prestamo>(this.apiUrl, prestamo);
  }

 // Obtener todos los préstamos
 getPrestamos(): Observable<Prestamo[]> {
  return this.http.get<Prestamo[]>(this.apiUrl).pipe(
    map(prestamos => prestamos.map(p => ({
      ...p,
      nombreItem: p.item?.nombre ?? '',
      formato: p.item?.formato?.nombre ?? '',
      tipoItem: p.item?.tipo?.nombre ?? '',
      ubicacion: p.item?.ubicacion ?? '',
      estadoItem: p.item?.estado ?? '',
      nombrePersona: p.persona?.nombre ?? '',
      emailPersona: p.persona?.email ?? '',
      telefonoPersona: p.persona?.telefono ?? '',
      direccionPersona: p.persona?.direccion ?? '',
      fechaDevolucion: p.fechaDevolucion ? p.fechaDevolucion : 'EN PRESTAMO'
    }))
    ),
    catchError(this.handleError)
  );
}

  // Obtener préstamos por persona
  getPrestamosPorPersona(personaId: number): Observable<Prestamo[]> {
    return this.http.get<Prestamo[]>(`${this.apiUrl}/personas/${personaId}`);
  }

  // Obtener un préstamo por ID
  getPrestamoById(id: number): Observable<Prestamo> {
    return this.http.get<Prestamo>(`${this.apiUrl}/${id}`);
  }

  // Actualizar un préstamo por ID
  updatePrestamo(prestamo: Prestamo): Observable<Prestamo> {
    return this.http.put<Prestamo>(`${this.apiUrl}/${prestamo.id}`, prestamo);
  }

  // Eliminar un préstamo por ID
  deletePrestamo(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  devolverPrestamo(id: number): Observable<Prestamo> {
    return this.http.put<Prestamo>(`${this.apiUrl}/devolver/${id}`, {}).pipe(
      catchError(this.handleError)
    );
  }

  getAvailableItems(): Observable<{nombre: string; id: number }[]> {
    return this.http.get<any[]>(this.itemsUrl).pipe( // Change the type to any[]
      map(items => items.map(item => ({
        id: item.id,
        nombre: item.nombre
      }))),
      catchError(this.handleError)
    );
  }

  private handleError(error: any) {
    let errorMessage = 'Error desconocido';
    if (error.error?.mensaje) {
      errorMessage = error.error.mensaje;
    } else if (error.message) {
      errorMessage = error.message;
    }
    return throwError(() => new Error(errorMessage));
  }
}