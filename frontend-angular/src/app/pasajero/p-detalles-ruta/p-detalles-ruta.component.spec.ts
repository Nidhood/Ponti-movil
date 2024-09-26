import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PDetallesRutaComponent } from './p-detalles-ruta.component';
import { RutaDto } from '../../dto/pasajero/ruta-dto';

describe('PDetallesRutaComponent', () => {
  let component: PDetallesRutaComponent;
  let fixture: ComponentFixture<PDetallesRutaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PDetallesRutaComponent ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PDetallesRutaComponent);
    component = fixture.componentInstance;
    component.ruta = {
      codigo: 'TEST',
      horario: { dia: 'Lunes', horaInicio: '08:00', horaFin: '20:00' },
      estaciones: [],
      buses: [],
      conductores: []
    } as RutaDto;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
