import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Persona } from './persona';

@Injectable({
  providedIn: 'root'
})
export class PersonaService {
  private url = 'http://localhost:4200/api/personas'; // Corrected URL

  constructor(private http: HttpClient) { }

  // Obtener todas las personas
  getAllPersonas(): Observable<Persona[]> {
    return this.http.get<Persona[]>(this.url);
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
  updatePersona(id: number, persona: Persona): Observable<Persona> {
    return this.http.put<Persona>(`${this.url}/${id}`, persona);
  }

  // Eliminar una persona
  deletePersona(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
