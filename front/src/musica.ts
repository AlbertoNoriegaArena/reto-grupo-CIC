// src/musica.ts
export interface Musica {
  itemId: number;
  genero: string;
  cantante: string;
  album: string;
  duracion: number;
  nombrePersona: string;
  albumIngresado: string;
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
