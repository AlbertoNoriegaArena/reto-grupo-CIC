import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormulariomusicaComponent } from './formulariomusica.component';

describe('FormulariomusicaComponent', () => {
  let component: FormulariomusicaComponent;
  let fixture: ComponentFixture<FormulariomusicaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormulariomusicaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormulariomusicaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
