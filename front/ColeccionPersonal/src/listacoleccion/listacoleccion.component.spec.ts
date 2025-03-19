import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListacoleccionComponent } from './listacoleccion.component';

describe('ListacoleccionComponent', () => {
  let component: ListacoleccionComponent;
  let fixture: ComponentFixture<ListacoleccionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListacoleccionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListacoleccionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
