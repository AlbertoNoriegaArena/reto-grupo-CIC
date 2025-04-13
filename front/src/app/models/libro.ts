export interface Libro {
  id: number;
  autor: string;
  numeroPaginas: number;  
  isbn: string;
  editorial: string;
  fechaPublicacion: string;
  item: {
    id: number;
    nombre: string;
    ubicacion: string;
    tipo: {
      id: number;
      nombre: string;
    };
    formato: {
      id: number;
      nombre: string;
    };
    fecha: string;
    estado: string;
  };
}
