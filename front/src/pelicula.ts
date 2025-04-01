// src/pelicula.ts
export interface Pelicula {
  id: number;
  director: string;
  duracion: number;
  genero: string;
  fechaEstreno: string;
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
