import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Libro } from './libro';
import { catchError, map } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class LibroService {
    private url = 'http://localhost:4200/api/libros';
    constructor(private httpClient: HttpClient) { }

    insertar(libro: Libro): Observable<Libro> {
        return this.httpClient.post<Libro>(this.url, libro);
    }

    getLibros(): Observable<Libro[]> {
        return this.httpClient.get<Libro[]>(this.url);
    }

    buscarUno(itemId: number): Observable<Libro> {
        return this.httpClient.get<Libro>(`${this.url}/${itemId}`);
    }

    actualizar(libro: Libro) {
        return this.httpClient.put(`/api/libros/${libro.item.id}`, libro);
    }

    borrar(id: number): Observable<{ success: boolean; message: string }> {
        return this.httpClient.delete<{ success: boolean; message: string }>(`${this.url}/${id}`).pipe(
            map(response => {
                return { success: true, message: 'Libro eliminado' }; // Si se borra correctamente
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
