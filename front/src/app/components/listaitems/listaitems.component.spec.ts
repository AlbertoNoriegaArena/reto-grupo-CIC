import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaitemsComponent } from './listaitems.component';

describe('ListaitemsComponent', () => {
  let component: ListaitemsComponent;
  let fixture: ComponentFixture<ListaitemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaitemsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaitemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
