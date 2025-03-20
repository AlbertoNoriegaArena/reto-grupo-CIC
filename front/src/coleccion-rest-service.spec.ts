import { TestBed } from '@angular/core/testing';

import { ColeccionRestService } from './coleccion-rest-service';

describe('LibroRestService', () => {
  let service: ColeccionRestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ColeccionRestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
