import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetallecoleccionComponent } from './detallecoleccion.component';

describe('DetallecoleccionComponent', () => {
  let component: DetallecoleccionComponent;
  let fixture: ComponentFixture<DetallecoleccionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetallecoleccionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetallecoleccionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
