import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioitemsComponent } from './formularioitems.component';

describe('FormularioitemsComponent', () => {
  let component: FormularioitemsComponent;
  let fixture: ComponentFixture<FormularioitemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioitemsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormularioitemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
