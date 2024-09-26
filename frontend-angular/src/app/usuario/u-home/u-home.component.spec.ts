import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UHomeComponent } from './u-home.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('HomeComponent', () => {
  let component: UHomeComponent;
  let fixture: ComponentFixture<UHomeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule // Agrega esto para que reconozca <router-outlet>
      ],
      declarations: [UHomeComponent]
    });
    fixture = TestBed.createComponent(UHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
