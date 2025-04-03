export interface Prestamo {
  id: number;
  item: {
    id: number;
    nombre: string;
    tipo: {
      id: number;
      nombre: string;
    };
    formato: {
      id: number;
      nombre: string;
    };
    ubicacion: string;
    fecha: string;
    estado: string;
  };
  persona: {
    id: number;
    nombre: string;
    direccion: string;
    email: string;
    telefono: string;
  };
  fechaPrestamo: string;
  fechaDevolucion: string | null;
  fechaPrevistaDevolucion: string;
  borrado: boolean;
  itemId: number;
}