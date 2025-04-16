import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment'; 

@Injectable({
  providedIn: 'root'
})
export class VistaprincipalService {
  private urlItems = `${environment.apiUrl}/items`;
  private urlLibros = `${environment.apiUrl}/libros`;
  private urlPeliculas = `${environment.apiUrl}/pelicula`;
  private urlMusica = `${environment.apiUrl}/musica`;
  private urlUsuarios = `${environment.apiUrl}/personas`;
  private urlPrestamos = `${environment.apiUrl}/prestamos`;
  private urlFormatos = `${environment.apiUrl}/formatos`;

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
