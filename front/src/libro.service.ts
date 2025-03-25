import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Libro } from './libro';

@Injectable({
    providedIn: 'root'
})
export class LibroService {
    private url = 'http://localhost:4200/api/libros'; 
    constructor(private http: HttpClient) { }

    getLibros(): Observable<Libro[]> {
        return this.http.get<Libro[]>(this.url);
    }

    insertar(libro: Libro): Observable<Libro> {
        return this.http.post<Libro>(this.url, libro);
    }
   
}
