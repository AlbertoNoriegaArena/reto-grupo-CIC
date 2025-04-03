import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaprestamosComponent } from './listaprestamos.component';

describe('ListaprestamosComponent', () => {
  let component: ListaprestamosComponent;
  let fixture: ComponentFixture<ListaprestamosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaprestamosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaprestamosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
