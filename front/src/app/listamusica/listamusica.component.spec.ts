import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListamusicaComponent } from './listamusica.component';

describe('ListamusicaComponent', () => {
  let component: ListamusicaComponent;
  let fixture: ComponentFixture<ListamusicaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListamusicaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListamusicaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
