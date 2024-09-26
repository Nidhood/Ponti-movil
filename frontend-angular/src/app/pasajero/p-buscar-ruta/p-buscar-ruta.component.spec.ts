import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PBuscarRutaComponent } from './p-buscar-ruta.component';

describe('PBuscarRutaComponent', () => {
  let component: PBuscarRutaComponent;
  let fixture: ComponentFixture<PBuscarRutaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PBuscarRutaComponent]
    });
    fixture = TestBed.createComponent(PBuscarRutaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
