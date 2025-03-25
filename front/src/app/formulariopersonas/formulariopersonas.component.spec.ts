import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormulariopersonasComponent } from './formulariopersonas.component';

describe('FormulariopersonasComponent', () => {
  let component: FormulariopersonasComponent;
  let fixture: ComponentFixture<FormulariopersonasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormulariopersonasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormulariopersonasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
