import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { forkJoin, Observable, throwError } from 'rxjs';
import { Formato } from './formato';
import { catchError, map, switchMap, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class FormatoService {
  private url = 'http://localhost:4200/api/formatos';
  private urltipoFormatos = 'http://localhost:4200/api/TipoItemFormatos';
  private urltipoItem = 'http://localhost:4200/api/tipoItems';
  private urlItem = 'http://localhost:4200/api/items';

  constructor(private http: HttpClient) { }

  insertar(formato: Formato): Observable<Formato> {
    return this.http.post<Formato>(this.url, formato);
  }

  getFormatos(): Observable<Formato[]> {
    return this.http.get<Formato[]>(this.url);
  }

  buscarUno(id: number): Observable<Formato> {
    return this.http.get<Formato>(`${this.url}/${id}`);
  }

  actualizar(formato: Formato): Observable<Formato> {
    return this.http.put<Formato>(`${this.url}/${formato.id}`, formato);
  }

  deleteFormato(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

  getFormatosAgrupadosPorTipo(): Observable<any[]> {
    return this.http.get<any[]>(this.urltipoFormatos);
  }

  getTipoItems(): Observable<any> {
    return this.http.get<any>(`${this.urltipoItem}`);
  }

  crearRelacionTipoItemFormato(tipoItemId: number, formatoId: number): Observable<any> {
    const payload = {
      tipoItemId,
      formatoId
    };
    return this.http.post(this.urltipoFormatos, payload);
  }

  isFormatoAssociatedWithItem(formatoId: number): Observable<boolean> {
    return this.http.get<any[]>(this.urlItem).pipe(
      map(items => items.some(item => item.formato?.id === formatoId)),
      catchError(this.handleError)
    );
  }

  private deleteTipoItemFormatoRelationship(tipoItemId: number, formatoId: number): Observable<any> {
    const url = `${this.urltipoFormatos}/eliminar?tipoItemId=${tipoItemId}&formatoId=${formatoId}`;
    return this.http.delete(url);
  }


  borrar(id: number): Observable<void> {
    return this.isFormatoAssociatedWithItem(id).pipe(
      switchMap(isAssociated => {
        if (isAssociated) {
          return throwError(() => new Error('El formato está asociado a un ítem y no se puede eliminar'));
        } else {
          // Obtener relaciones tipoItem-formato para este formato
          return this.http.get<any[]>(`${this.urltipoFormatos}/porFormato?formatoId=${id}`).pipe(
            switchMap(relaciones => {
              const deleteRequests = relaciones.map(relacion =>
                this.deleteTipoItemFormatoRelationship(relacion.tipoItem.id, id)
              );
              if (deleteRequests.length > 0) {
                // Si hay relaciones, esperamos a que se eliminen todas antes de eliminar el formato
                return forkJoin(deleteRequests).pipe(
                  switchMap(() => this.deleteFormato(id)) // Eliminar el formato después de las relaciones
                );
              } else {
                // Si no hay relaciones, directamente eliminamos el formato
                return this.deleteFormato(id);
              }
            })
          );
        }
      }),
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

}
