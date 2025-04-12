export interface ItemDTO {
  nombre: string;
  tipoId: number;
  formatoId: number;
  ubicacion: string;
  fecha: string;
  estado: string;

  // SOLO si tipoId = LIBRO
  autor?: string;
  isbn?: string;
  editorial?: string;
  numeroPaginas?: number;
  fechaPublicacion?: string;

  // SOLO si tipoId = MUSICA
  generoMusica?: string;
  cantante?: string;
  album?: string;
  duracionMusica?: string;

  // Solo si tipoId = PELICULA
  director?: string;
  duracionPelicula?: number;
  generoPelicula?: string;
  fechaEstreno?: string;
}
