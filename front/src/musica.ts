// src/musica.ts
export interface Musica {
  itemId: number;
  genero: string;
  cantante: string;
  album: string;
  duracion: string; // Cambiado de number a string para coincidir con el JSON
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