// src/musica.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Musica } from '../models/musica'; // Import the Musica interface
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MusicaService {
  private url = 'http://localhost:4200/api/musica'; // Replace with your actual backend URL
  private formatosUrl = 'http://localhost:4200/api/TipoItemFormatos';

  constructor(private httpClient: HttpClient) { }

  insertar(musica: Musica): Observable<Musica> {
    return this.httpClient.post<Musica>(this.url, musica);
  }

  getMusica(): Observable<Musica[]> {
    return this.httpClient.get<Musica[]>(this.url).pipe(
      map(musica => musica.map(m => ({
        ...m,
        nombre: m.item?.nombre ?? '',
        formato: m.item?.formato?.nombre ?? ''
      }))),
      catchError(this.handleError)
    );
  }

  buscarUno(itemId: number): Observable<Musica> {
    return this.httpClient.get<Musica>(`${this.url}/${itemId}`);
  }

  actualizar(musica: Musica) {
    return this.httpClient.put(`${this.url}/${musica.item.id}`, musica);
  }

  borrar(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.url}/${id}`).pipe(
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

  // Método para obtener los formatos
  getFormatos(tipoItemSeleccionado: string): Observable<{ nombre: string; id: number }[]> {
    return this.httpClient.get<any[]>(this.formatosUrl).pipe(
      map(data => data
        .filter(item => item.tipoItem.nombre === tipoItemSeleccionado)
        .map(item => ({
          id: item.formato.id,
          nombre: item.formato.nombre
        }))
      )
    );
  }
}
