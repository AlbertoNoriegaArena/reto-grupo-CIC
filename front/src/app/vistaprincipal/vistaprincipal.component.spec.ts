import { ComponentFixture, TestBed } from '@angular/core/testing';
import { VistaprincipalComponent } from './vistaprincipal.component';

describe('VistaprincipalComponent', () => {
  let component: VistaprincipalComponent;
  let fixture: ComponentFixture<VistaprincipalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VistaprincipalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VistaprincipalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
