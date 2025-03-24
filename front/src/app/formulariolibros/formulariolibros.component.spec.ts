import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormulariolibrosComponent } from './formulariolibros.component';

describe('FormulariolibrosComponent', () => {
  let component: FormulariolibrosComponent;
  let fixture: ComponentFixture<FormulariolibrosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormulariolibrosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormulariolibrosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
