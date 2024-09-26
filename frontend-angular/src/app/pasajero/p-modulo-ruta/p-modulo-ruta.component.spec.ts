import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PModuloRutaComponent } from './p-modulo-ruta.component';

describe('PModuloRutaComponent', () => {
  let component: PModuloRutaComponent;
  let fixture: ComponentFixture<PModuloRutaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PModuloRutaComponent]
    });
    fixture = TestBed.createComponent(PModuloRutaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
