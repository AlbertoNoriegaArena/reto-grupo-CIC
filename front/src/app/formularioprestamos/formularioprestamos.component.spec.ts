import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioprestamosComponent } from './formularioprestamos.component';

describe('FormularioprestamosComponent', () => {
  let component: FormularioprestamosComponent;
  let fixture: ComponentFixture<FormularioprestamosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioprestamosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormularioprestamosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
