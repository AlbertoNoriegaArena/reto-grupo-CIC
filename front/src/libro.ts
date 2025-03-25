// src/libro.ts
export interface Libro {
    itemId: number;
    isbn: string;
    editorial: string;
    numeroPaginas: number;

    nombrePersona: string;
    libroIngresado: string;
    fechaRecogida: string;
    fechaDevolucion: string;
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