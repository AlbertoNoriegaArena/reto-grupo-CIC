import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleMusicaComponent } from './detalle-musica.component';

describe('DetalleMusicaComponent', () => {
  let component: DetalleMusicaComponent;
  let fixture: ComponentFixture<DetalleMusicaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalleMusicaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalleMusicaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
