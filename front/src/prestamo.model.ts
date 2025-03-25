export interface Prestamo {
  id: number;
  usuarioId: number;
  monto: number;
  fechaPrestamo: string;
  fechaDevolucion: string;
  estado: string;  // Por ejemplo, "activo", "devuelto"
}

  