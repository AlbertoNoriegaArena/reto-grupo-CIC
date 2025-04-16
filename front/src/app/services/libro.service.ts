import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Libro } from '../models/libro';
import { catchError, map } from 'rxjs/operators';
import { environment } from '../../environments/environment'; 

@Injectable({
    providedIn: 'root'
})
export class LibroService {
    private url = `${environment.apiUrl}/libros`;
    private formatosUrl = `${environment.apiUrl}/TipoItemFormatos`;
    constructor(private httpClient: HttpClient) { }

    insertar(libro: Libro): Observable<Libro> {
        return this.httpClient.post<Libro>(this.url, libro);
    }

    getLibros(): Observable<Libro[]> {
        return this.httpClient.get<Libro[]>(this.url).pipe(
            map(libros => libros.map(l => ({
                ...l,
                nombre: l.item?.nombre ?? '',
                formato: l.item?.formato?.nombre ?? ''
            }))),
            catchError(this.handleError)
        );
    }

    buscarUno(itemId: number): Observable<Libro> {
        return this.httpClient.get<Libro>(`${this.url}/${itemId}`);
    }

    actualizar(libro: Libro) {
        return this.httpClient.put(`${this.url}/${libro.item.id}`, libro);
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
