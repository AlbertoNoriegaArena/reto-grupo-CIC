import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Libro } from './libro';

@Injectable({
    providedIn: 'root'
})
export class LibroService {
    private apiUrl = 'http://localhost:8080/api/libros'; // Replace with your actual API endpoint

    constructor(private http: HttpClient) { }

    getLibros(): Observable<Libro[]> {
        return this.http.get<Libro[]>(this.apiUrl);
    }

    insertar(libro: Libro): Observable<Libro> {
        return this.http.post<Libro>(this.apiUrl, libro);
    }
    // Add other methods as needed (update, delete, etc.)
}
