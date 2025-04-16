import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Item } from '../models/item';
import { ItemDTO } from '../models/itemDTO';
import { catchError, map } from 'rxjs/operators';
import { environment } from '../../environments/environment'; 

@Injectable({
  providedIn: 'root'
})
export class ItemService {
  private url = `${environment.apiUrl}/items`;
  private formatosUrl = `${environment.apiUrl}/TipoItemFormatos`;

  constructor(private http: HttpClient) {}

  insertar(itemDTO: ItemDTO): Observable<Item> {
    return this.http.post<Item>(this.url, itemDTO);  // Enviamos directamente el DTO sin el id
  }

    getItems(): Observable<Item[]> {
      return this.http.get<Item[]>(this.url).pipe(
        catchError(this.handleError) 
      );
    }

  // Obtener un artículo por su id
  getItemById(id: number): Observable<Item> {
    return this.http.get<Item>(`${this.url}/${id}`);
  }

  actualizar(item: Item): Observable<Item> {
    return this.http.put<Item>(`${this.url}/${item.id}`, item);
  }
  
    borrar(id: number): Observable<void> {
      return this.http.delete<void>(`${this.url}/${id}`).pipe(
        catchError(this.handleError)
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

    getFormatos(tipoItemSeleccionado: string): Observable<{ nombre: string; id: number }[]> {
      return this.http.get<any[]>(this.formatosUrl).pipe(
        map(data => data
          .filter(item => item.tipoItem.nombre === tipoItemSeleccionado)
          .map(item => ({
            id: item.formato.id,
            nombre: item.formato.nombre
          }))
        )
      );
    }
}
