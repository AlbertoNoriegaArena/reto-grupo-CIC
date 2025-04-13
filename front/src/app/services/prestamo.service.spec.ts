import { TestBed } from '@angular/core/testing';

import { PrestamosService } from './prestamo.service';

describe('PrestamosService', () => {
  let service: PrestamosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrestamosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
