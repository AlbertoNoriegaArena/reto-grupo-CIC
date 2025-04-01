// src/musica.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Musica } from './musica'; // Import the Musica interface
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MusicaService {
  private url = 'http://localhost:4200/api/musica'; // Replace with your actual backend URL

  constructor(private httpClient: HttpClient) { }

  insertar(musica: Musica): Observable<Musica> {
    return this.httpClient.post<Musica>(this.url, musica);
  }

  getMusica(): Observable<Musica[]> {
    return this.httpClient.get<Musica[]>(this.url);
  }

  buscarUno(itemId: number): Observable<Musica> {
    return this.httpClient.get<Musica>(`${this.url}/${itemId}`);
  }

  actualizar(musica: Musica) {
    return this.httpClient.put(`/api/musica/${musica.item.id}`, musica);
  }

  borrar(id: number): Observable<{ success: boolean; message: string }> {
    return this.httpClient.delete<{ success: boolean; message: string }>(`${this.url}/${id}`).pipe(
      map(response => {
        return { success: true, message: 'Música eliminada' }; // Si se borra correctamente
      }),
      catchError((error) => {
        let errorMessage = 'Error desconocido';

        // Si el backend envía un error con estructura JSON
        if (error.error && error.error.mensaje) {
          errorMessage = error.error.mensaje;  // Extrae el mensaje del backend
        } else if (error.message) {
          errorMessage = error.message;
        }

        return [{ success: false, message: errorMessage }]; // Devuelve un error controlado
      })
    );
  }
}
