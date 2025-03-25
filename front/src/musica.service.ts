// src/musica.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Musica } from './musica'; // Import the Musica interface

@Injectable({
    providedIn: 'root'
})
export class MusicaService {
    getMusicas() {
        throw new Error('Method not implemented.');
    }
    private url = 'http://localhost:4200/api/musica'; // Replace with your actual backend URL

    constructor(private httpClient: HttpClient) { }

    insertar(musica: Musica): Observable<Musica> {
        return this.httpClient.post<Musica>(this.url, musica);
    }

    buscarTodos(): Observable<Musica[]> {
        return this.httpClient.get<Musica[]>(this.url);
    }

    buscarUno(album: string): Observable<Musica> {
        return this.httpClient.get<Musica>(`${this.url}/${album}`);
    }

    actualizar(musica: Musica): Observable<Musica> {
        return this.httpClient.put<Musica>(this.url, musica);
    }

    borrar(album: string): Observable<void> {
        return this.httpClient.delete<void>(`${this.url}/${album}`);
    }
    //Corrected method
    getMusica(): Observable<Musica[]> {
        return this.httpClient.get<Musica[]>(this.url);
    }
}
