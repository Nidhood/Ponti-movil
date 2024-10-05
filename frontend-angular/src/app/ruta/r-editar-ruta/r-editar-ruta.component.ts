import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Button} from 'primeng/button';
import {RModuloEstacionComponent} from '../r-modulo-estacion/r-modulo-estacion.component';
import {GestionarRutasService} from '../../share/gestionar-rutas.service';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {RutaDto} from '../../dto/gestionar-rutas/ruta/ruta-dto';
import {EstacionDTO} from '../../dto/gestionar-rutas/estacion/estacion-dto';
import {CdkDropList} from '@angular/cdk/drag-drop';
import {NgForOf, NgIf} from '@angular/common';
import {CalendarModule} from 'primeng/calendar';
import {ChipsModule} from 'primeng/chips';

@Component({
  selector: 'app-r-editar-ruta',
  standalone: true,
  imports: [
    FormsModule,
    Button,
    RModuloEstacionComponent,
    ConfirmDialogModule,
    CdkDropList,
    NgForOf,
    NgIf,
    CalendarModule,
    ChipsModule
  ],
  templateUrl: './r-editar-ruta.component.html',
  styleUrl: './r-editar-ruta.component.css'
})

export class REditarRutaComponent {
  @Input() ruta: RutaDto | undefined;
  @Output() close = new EventEmitter<void>();
  @Output() save = new EventEmitter<RutaDto>();

  estacionesAsignadas: EstacionDTO[] = [];
  estacionesNoAsignadas: EstacionDTO[] = [];

  constructor(private gestionarRutasService: GestionarRutasService) {}

  ngOnInit() {
    if (this.ruta) {
      this.cargarEstaciones();
    }
  }

  cargarEstaciones() {
    if (this.ruta && this.ruta.id) {
      this.gestionarRutasService.obtenerEstacionesPorRuta(this.ruta.id).subscribe(
        (estaciones: EstacionDTO[]) => {
          this.estacionesAsignadas = estaciones.filter(e => e.dentroRuta);
          this.estacionesNoAsignadas = estaciones.filter(e => !e.dentroRuta);
        },
        error => console.error('Error al cargar estaciones:', error)
      );
    }
  }

  asignarEstacion(estacion: EstacionDTO) {
    this.estacionesAsignadas.push(estacion);
    this.estacionesNoAsignadas = this.estacionesNoAsignadas.filter(e => e !== estacion);
    estacion.dentroRuta = true;
  }

  desasignarEstacion(estacion: EstacionDTO) {
    this.estacionesNoAsignadas.push(estacion);
    this.estacionesAsignadas = this.estacionesAsignadas.filter(e => e !== estacion);
    estacion.dentroRuta = false;
  }

  saveChanges() {
    if (this.ruta) {
      this.ruta.estaciones = this.estacionesAsignadas;
      this.gestionarRutasService.actualizarRuta(this.ruta).subscribe(
        (rutaActualizada: RutaDto) => {
          this.save.emit(rutaActualizada);
        },
        error => console.error('Error al actualizar la ruta:', error)
      );
    }
  }

  canDelete(): boolean {
    return this.ruta ? (this.ruta.conductores.length === 0 && this.ruta.buses.length === 0) : false;
  }

  deleteRoute() {
    if (this.canDelete() && this.ruta && this.ruta.id) {
      this.gestionarRutasService.eliminarRuta(this.ruta.id).subscribe(
        () => {
          this.close.emit();
        },
        error => console.error('Error al eliminar la ruta:', error)
      );
    }
  }

  closeEdit() {
    this.close.emit();
  }
}
