import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VistaprincipalService {
  private urlItems = 'http://localhost:4200/api/items';
  private urlLibros = 'http://localhost:4200/api/libros';
  private urlPeliculas = 'http://localhost:4200/api/pelicula';
  private urlMusica = 'http://localhost:4200/api/musica';
  private urlUsuarios = 'http://localhost:4200/api/personas';
  private urlPrestamos = 'http://localhost:4200/api/prestamos';
  private urlFormatos = 'http://localhost:4200/api/formatos'; 

  constructor(private http: HttpClient) {}

  getItems(): Observable<any[]> {
    return this.http.get<any[]>(`${this.urlItems}`);
  }

  getLibros(): Observable<any[]> {
    return this.http.get<any[]>(`${this.urlLibros}`);
  }

  getPeliculas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.urlPeliculas}`);
  }

  getMusica(): Observable<any[]> {
    return this.http.get<any[]>(`${this.urlMusica}`);
  }

  getUsuarios(): Observable<any[]> {
    return this.http.get<any[]>(`${this.urlUsuarios}`);
  }

  getPrestamos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.urlPrestamos}`);
  }

  getFormatos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.urlFormatos}`);
  }
}
