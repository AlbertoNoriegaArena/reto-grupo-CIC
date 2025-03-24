import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListapeliculasComponent } from './listapeliculas.component';

describe('ListapeliculasComponent', () => {
  let component: ListapeliculasComponent;
  let fixture: ComponentFixture<ListapeliculasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListapeliculasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListapeliculasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
