import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Prestamo } from './prestamo.model';

@Injectable({
  providedIn: 'root'
})
export class PrestamosService {
  private apiUrl = 'http://localhost:4200/api/prestamos'; // Asegúrate de que esta sea la URL de tu backend

  constructor(private http: HttpClient) {}

  // Obtener todos los préstamos
  getAllPrestamos(): Observable<Prestamo[]> {
    return this.http.get<Prestamo[]>(this.apiUrl);
  }

  //Obtener préstamos por persona
  getPrestamosPorPersona(personaId: number): Observable<Prestamo[]> {
    return this.http.get<Prestamo[]>(`${this.apiUrl}/personas/${personaId}`);
  }

  // Crear un nuevo préstamo
  createPrestamo(prestamo: Prestamo): Observable<Prestamo> {
    return this.http.post<Prestamo>(this.apiUrl, prestamo);
  }

  // Obtener un préstamo por ID
  getPrestamoById(id: number): Observable<Prestamo> {
    return this.http.get<Prestamo>(`${this.apiUrl}/${id}`);
  }

  // Actualizar un préstamo por ID
  updatePrestamo(id: number, prestamo: Prestamo): Observable<Prestamo> {
    return this.http.put<Prestamo>(`${this.apiUrl}/${id}`, prestamo);
  }

  // Eliminar un préstamo por ID
  deletePrestamo(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Marcar un préstamo como devuelto
  devolverPrestamo(id: number): Observable<Prestamo> {
    return this.http.put<Prestamo>(`${this.apiUrl}/devolver/${id}`, {});
  }
}