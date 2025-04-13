import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormulariopeliculasComponent } from './formulariopeliculas.component';

describe('FormulariopeliculasComponent', () => {
  let component: FormulariopeliculasComponent;
  let fixture: ComponentFixture<FormulariopeliculasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormulariopeliculasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormulariopeliculasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
