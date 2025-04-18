import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetallePrestamoComponent } from './detalle-prestamo.component';

describe('DetallePrestamoComponent', () => {
  let component: DetallePrestamoComponent;
  let fixture: ComponentFixture<DetallePrestamoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetallePrestamoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetallePrestamoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
