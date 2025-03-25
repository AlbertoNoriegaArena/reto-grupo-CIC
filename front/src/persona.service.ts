import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Persona } from './persona.model';

@Injectable({
  providedIn: 'root'
})

export class PersonaService {

  // URL de la API de Spring Boot (ajustar según tu configuración)
  private apiUrl = 'http://localhost:8080/api/personas'; // Cambia esta URL si es necesario

  // Constructor con inyección de HttpClient
  constructor(private http: HttpClient) { }

  // Obtener todas las personas
  getAllPersonas(): Observable<Persona[]> {
    return this.http.get<Persona[]>(this.apiUrl);  // Realiza una solicitud GET para obtener la lista de personas
  }

  // Obtener persona por ID
  getPersonaById(id: number): Observable<Persona> {
    return this.http.get<Persona>(`${this.apiUrl}/${id}`);  // Realiza una solicitud GET con el ID
  }

  // Crear una nueva persona
  createPersona(persona: Persona): Observable<Persona> {
    return this.http.post<Persona>(this.apiUrl, persona);  // Realiza una solicitud POST para crear una persona
  }

  // Actualizar una persona existente
  updatePersona(id: number, persona: Persona): Observable<Persona> {
    return this.http.put<Persona>(`${this.apiUrl}/${id}`, persona);  // Realiza una solicitud PUT para actualizar
  }

  // Eliminar una persona
  deletePersona(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);  // Realiza una solicitud DELETE para eliminar una persona
  }
}