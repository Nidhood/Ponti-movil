import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PDetallesRutaComponent } from './p-detalles-ruta.component';

describe('PDetallesRutaComponent', () => {
  let component: PDetallesRutaComponent;
  let fixture: ComponentFixture<PDetallesRutaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PDetallesRutaComponent]
    });
    fixture = TestBed.createComponent(PDetallesRutaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
