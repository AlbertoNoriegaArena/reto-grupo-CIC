
export class Prestamo {
  tipo: string;
  nombre: string;
  fechaPrestamo: string;
  fechaDevolucion: string;

  // Constructor para inicializar las propiedades
  constructor(tipo: string = '', nombre: string = '', fechaPrestamo: string = '', fechaDevolucion: string = '') {
    this.tipo = tipo;
    this.nombre = nombre;
    this.fechaPrestamo = fechaPrestamo;
    this.fechaDevolucion = fechaDevolucion;
  }
}
