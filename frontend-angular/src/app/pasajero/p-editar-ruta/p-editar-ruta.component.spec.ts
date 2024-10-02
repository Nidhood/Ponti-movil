import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PEditarRutaComponent } from './p-editar-ruta.component';

describe('PEditarRutaComponent', () => {
  let component: PEditarRutaComponent;
  let fixture: ComponentFixture<PEditarRutaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PEditarRutaComponent]
    });
    fixture = TestBed.createComponent(PEditarRutaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
