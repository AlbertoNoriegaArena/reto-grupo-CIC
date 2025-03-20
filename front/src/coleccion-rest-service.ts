import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Coleccion } from './coleccion';

@Injectable({
  providedIn: 'root'
})
export class ColeccionRestService {

  constructor(private httpClient:HttpClient) { 



  }
  public buscarTodos():Observable<Coleccion[]> {

    return this.httpClient.get<Coleccion[]>("http://localhost:8080/webapi/coleccion");

  }

  public insertar(coleccion:Coleccion):Observable<Coleccion> {

    return this.httpClient.post<Coleccion>("http://localhost:8080/webapi/coleccion", coleccion);
}

  public buscarUno(tipo:String):Observable<Coleccion> {

  return this.httpClient.get<Coleccion>(`http://localhost:8080/webapi/coleccion/${tipo}`);

    }
  }
