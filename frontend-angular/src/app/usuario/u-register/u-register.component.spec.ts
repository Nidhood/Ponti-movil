import { ComponentFixture, TestBed } from '@angular/core/testing';

import { URegisterComponent } from './u-register.component';

describe('RegisterComponent', () => {
  let component: URegisterComponent;
  let fixture: ComponentFixture<URegisterComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [URegisterComponent]
    });
    fixture = TestBed.createComponent(URegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
