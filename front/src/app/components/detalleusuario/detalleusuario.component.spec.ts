import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleusuarioComponent } from './detalleusuario.component';

describe('DetalleusuarioComponent', () => {
  let component: DetalleusuarioComponent;
  let fixture: ComponentFixture<DetalleusuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalleusuarioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalleusuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
