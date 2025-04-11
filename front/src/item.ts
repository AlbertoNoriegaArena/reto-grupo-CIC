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
  }
  