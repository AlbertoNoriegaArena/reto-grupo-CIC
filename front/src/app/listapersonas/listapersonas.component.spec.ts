import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ListapersonasComponent } from './listapersonas.component';

describe('ListapersonasComponent', () => {
  let component: ListapersonasComponent;
  let fixture: ComponentFixture<ListapersonasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListapersonasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListapersonasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
