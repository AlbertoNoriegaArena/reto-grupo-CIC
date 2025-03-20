import { Coleccion } from './coleccion';

describe('Coleccion', () => {
  it('should create an instance', () => {
    expect(new Coleccion('tipo', 'nombre', 1, 2, new Date())).toBeTruthy();
  });
});
