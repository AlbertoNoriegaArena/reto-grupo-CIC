import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Pelicula } from '../models/pelicula'; // Import the Pelicula interface
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PeliculaService {
  private url = 'http://localhost:4200/api/pelicula'; // Reemplaza esto con la URL de tu backend
  private formatosUrl = 'http://localhost:4200/api/TipoItemFormatos';

  constructor(private httpClient: HttpClient) { }

  insertar(pelicula: Pelicula): Observable<Pelicula> {
    return this.httpClient.post<Pelicula>(this.url, pelicula);
  }

  getPeliculas(): Observable<Pelicula[]> {
    return this.httpClient.get<Pelicula[]>(this.url).pipe(
      map(peliculas => peliculas.map(p => ({
        ...p,
        nombre: p.item?.nombre ?? '',
        formato: p.item?.formato?.nombre ?? ''
      }))),
      catchError(this.handleError)
    );
  }

  buscarUno(itemId: number): Observable<Pelicula> {
    return this.httpClient.get<Pelicula>(`${this.url}/${itemId}`);
  }

  actualizar(pelicula: Pelicula): Observable<Pelicula>  {
    return this.httpClient.put<Pelicula>(`${this.url}/${pelicula.item.id}`, pelicula);
  }

  borrar(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.url}/${id}`).pipe(
      catchError(this.handleError)
    );
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
