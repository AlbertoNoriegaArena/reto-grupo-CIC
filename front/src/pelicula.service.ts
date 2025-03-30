import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pelicula } from './pelicula'; // Import the Pelicula interface
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PeliculaService {
  private url = 'http://localhost:4200/api/pelicula'; // Reemplaza esto con la URL de tu backend

  constructor(private httpClient: HttpClient) { }

  insertar(pelicula: Pelicula): Observable<Pelicula> {
    return this.httpClient.post<Pelicula>(this.url, pelicula);
  }

  buscarTodos(): Observable<Pelicula[]> {
    return this.httpClient.get<Pelicula[]>(this.url);
  }

  buscarUno(itemId: number): Observable<Pelicula> {
    return this.httpClient.get<Pelicula>(`${this.url}/${itemId}`);
  }

  actualizar(pelicula: Pelicula) {
    return this.httpClient.put(`/api/pelicula/${pelicula.item.id}`, pelicula); 
  }
  

  borrar(id: number): Observable<{ success: boolean; message: string }> {
    return this.httpClient.delete<{ success: boolean; message: string }>(`${this.url}/${id}`).pipe(
      map(response => {
        return { success: true, message: 'Película eliminada' }; // Si se borra correctamente
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
  
  getPeliculas(): Observable<Pelicula[]> {
    return this.httpClient.get<Pelicula[]>(this.url);
  }

}
