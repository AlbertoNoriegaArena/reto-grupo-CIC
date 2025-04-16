import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { Persona } from '../models/persona';
import { catchError, map } from 'rxjs/operators';
import { environment } from '../../environments/environment'; 

@Injectable({
  providedIn: 'root'
})
export class PersonaService {
  private url = `${environment.apiUrl}/personas`;

  constructor(private http: HttpClient) { }

  // Obtener todas las personas
  getAllPersonas(): Observable<Persona[]> {
    return this.http.get<Persona[]>(this.url).pipe(
      map(personas => personas.map(p => ({
        ...p,
        nombre: p.nombre ?? '',  
        email: p.email ?? '',
        telefono: p.telefono ?? '',
        direccion: p.direccion ?? ''
      }))),
      catchError(this.handleError)
    );
  }
  
  // Obtener persona por ID
  getPersonaById(id: number): Observable<Persona> {
    return this.http.get<Persona>(`${this.url}/${id}`);
  }

  // Crear una nueva persona
  insertar(persona: Persona): Observable<Persona> { // Corrected method name and return type
    return this.http.post<Persona>(this.url, persona);
  }

  // Actualizar una persona existente
  updatePersona(persona: Persona): Observable<Persona> {
    return this.http.put<Persona>(`${this.url}/${persona.id}`, persona);
  }

  // Eliminar una persona
  deletePersona(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`).pipe(
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
