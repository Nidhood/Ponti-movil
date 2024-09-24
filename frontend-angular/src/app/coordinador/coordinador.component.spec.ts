import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoordinadorComponent } from './coordinador.component';

describe('CoordinadorComponent', () => {
  let component: CoordinadorComponent;
  let fixture: ComponentFixture<CoordinadorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CoordinadorComponent]
    });
    fixture = TestBed.createComponent(CoordinadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
