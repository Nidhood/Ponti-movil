import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { PBuscarRutaComponent } from './p-buscar-ruta.component';

describe('PBuscarRutaComponent', () => {
  let component: PBuscarRutaComponent;
  let fixture: ComponentFixture<PBuscarRutaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ FormsModule ],
      declarations: [ PBuscarRutaComponent ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PBuscarRutaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
