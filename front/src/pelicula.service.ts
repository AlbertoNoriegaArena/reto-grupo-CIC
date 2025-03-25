import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pelicula } from './pelicula'; // Import the Pelicula interface

@Injectable({
  providedIn: 'root'
})
export class PeliculaService {
  private url = 'http://localhost:4200/api/pelicula'; // Replace with your actual backend URL

  constructor(private httpClient: HttpClient) { }

  insertar(pelicula: Pelicula): Observable<Pelicula> {
    return this.httpClient.post<Pelicula>(this.url, pelicula);
  }

  buscarTodos(): Observable<Pelicula[]> {
    return this.httpClient.get<Pelicula[]>(this.url);
  }

  buscarUno(director: string): Observable<Pelicula> {
    return this.httpClient.get<Pelicula>(`${this.url}/${director}`);
  }

  actualizar(pelicula: Pelicula): Observable<Pelicula> {
    return this.httpClient.put<Pelicula>(this.url, pelicula);
  }

  // Método para borrar una película por ID
  borrar(itemId: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.url}/${itemId}`);
  }

  getPeliculas(): Observable<Pelicula[]> {
    return this.httpClient.get<Pelicula[]>(this.url);
  }

}
