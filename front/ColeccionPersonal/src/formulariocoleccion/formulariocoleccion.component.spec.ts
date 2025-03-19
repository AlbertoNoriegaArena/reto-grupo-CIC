import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormulariocoleccionComponent } from './formulariocoleccion.component';

describe('FormulariocoleccionComponent', () => {
  let component: FormulariocoleccionComponent;
  let fixture: ComponentFixture<FormulariocoleccionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormulariocoleccionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormulariocoleccionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
