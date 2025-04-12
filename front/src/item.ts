export interface Item {
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

    // Campos opcionales según el tipo
    autor?: string;
    isbn?: string;
    editorial?: string;
    numeroPaginas?: number;
    fechaPublicacion?: string;

    generoMusica?: string;
    cantante?: string;
    album?: string;
    duracionMusica?: string;

    director?: string;
    duracionPelicula?: number;
    generoPelicula?: string;
    fechaEstreno?: string;
  }
  