import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioformatosComponent } from './formularioformatos.component';

describe('FormularioformatosComponent', () => {
  let component: FormularioformatosComponent;
  let fixture: ComponentFixture<FormularioformatosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioformatosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormularioformatosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
