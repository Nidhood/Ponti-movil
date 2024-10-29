import { Component, EventEmitter, Input, NgZone, Output } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { MessageService, ConfirmationService } from 'primeng/api';
import { GestionarBusesService } from '../../share/gestionar-buses.service';
import {BRutaEnvioDTO} from '../../dto/gestionar-bus/bus-envio/bRutaEnvioDTO';
import {ToastModule} from 'primeng/toast';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {RModuloEstacionComponent} from '../../ruta/r-modulo-estacion/r-modulo-estacion.component';
import {SkeletonModule} from 'primeng/skeleton';
import {PickListModule} from 'primeng/picklist';
import {CalendarModule} from 'primeng/calendar';
import {FormsModule} from '@angular/forms';
import {MultiSelectModule} from 'primeng/multiselect';
import {AsyncPipe, NgIf} from '@angular/common';
import {RModuloRutaComponent} from '../../ruta/r-modulo-ruta/r-modulo-ruta.component';
import {BBusEnvioDTO} from '../../dto/gestionar-bus/bus-envio/bBusEnvioDTO';
import {CRutaRecibiendoDTO} from '../../dto/gestionar-conductores/conductor-recibiendo/cRutaRecibiendoDTO';
import {GestionarRutasService} from '../../share/gestionar-rutas.service';

interface DiaSemana {
  nombre: string;
  value: string;
}

@Component({
  selector: 'app-b-editar-bus',
  standalone: true,
  imports: [
    ToastModule,
    ConfirmDialogModule,
    RModuloEstacionComponent,
    SkeletonModule,
    PickListModule,
    CalendarModule,
    FormsModule,
    MultiSelectModule,
    AsyncPipe,
    RModuloRutaComponent,
    NgIf
  ],
  templateUrl: './b-editar-bus.component.html',
  styleUrls: ['./b-editar-bus.component.css'],
  providers: [MessageService, ConfirmationService]
})

export class BEditarBusComponent {
  @Input() bus: BBusEnvioDTO = new BBusEnvioDTO(
    '', // id
    '', // placa
    '', // modelo
    [], // días de la semana
    [], // rutas
  );
  @Output() close = new EventEmitter<void>();
  @Output() save = new EventEmitter<BBusEnvioDTO>();

  rutasAsignadas: BRutaEnvioDTO[] = [];
  rutasNoAsignadas: BRutaEnvioDTO[] = [];

  diasOptions!: DiaSemana[];
  selectedDias!: DiaSemana[];

  public loadingRutas$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(true);

  skeletonItems: any[] = Array(5).fill({});

  constructor(
    private gestionarBusesServices: GestionarBusesService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
  ) {}

  ngOnInit() {
    if (this.bus) {
      this.diasOptions = [
        { nombre: 'Lunes', value: 'Lunes' },
        { nombre: 'Martes', value: 'Martes' },
        { nombre: 'Miércoles', value: 'Miercoles' },
        { nombre: 'Jueves', value: 'Jueves' },
        { nombre: 'Viernes', value: 'Viernes' },
        { nombre: 'Sábado', value: 'Sabado' },
        { nombre: 'Domingo', value: 'Domingo' }
      ];
      this.cargarRutas();
      this.selectedDias = this.diasOptions.filter(option => this.bus.diasSemana.includes(option.value));
    }
  }

  cargarRutas() {
    this.loadingRutas$.next(true);
    if (this.bus && this.bus.id) {
      this.gestionarBusesServices.obtenerRutasPorBuses(this.bus.id).subscribe(
        (rutas: BRutaEnvioDTO[]) => {
          this.rutasAsignadas = rutas.filter(ruta => ruta.dentroBus);
          this.rutasNoAsignadas = rutas.filter(ruta => !ruta.dentroBus);
          this.loadingRutas$.next(false);
          this.actualizarEstadoRutas();
          this.messageService.add({ severity: 'success', summary: 'Rutas cargadas', detail: 'Las rutas han sido cargadas.', life: 3000 });
        },
        error => {
          this.loadingRutas$.next(false);
          this.messageService.add({ severity: 'error', summary: 'Error al cargar rutas', detail: error, life: 3000 });
        }
      );
    } else {
      this.loadingRutas$.next(false);
      this.messageService.add({ severity: 'error', summary: 'Error al cargar rutas', detail: 'No se pudo cargar las rutas.', life: 3000 });
    }
  }

  onMoveToTarget(event: any) {
    this.actualizarEstadoRutas();
  }

  onMoveToSource(event: any) {
    this.actualizarEstadoRutas();
  }

  actualizarEstadoRutas() {
    this.rutasAsignadas.forEach(ruta => {
      ruta.dentroBus = true;
    });

    this.rutasNoAsignadas.forEach(ruta => {
      ruta.dentroBus = false;
    });
  }

  actualizarDias() {
    this.bus.diasSemana = this.selectedDias.map(dia => dia.value);
  }

  saveChanges(event: Event) {
    if (this.bus) {
      this.confirmationService.confirm({
        target: event.target as EventTarget,
        message: '¿Estás seguro de que quieres guardar los cambios?',
        header: 'Confirmación de guardado',
        icon: 'pi pi-info-circle',
        acceptButtonStyleClass:"p-button-danger p-button-text",
        rejectButtonStyleClass:"p-button-text p-button-text",
        acceptIcon:"none",
        rejectIcon:"none",
        accept : () => {
          this.actualizarDias();
          this.bus.rutas = this.rutasAsignadas;
          this.gestionarBusesServices.actualizarBus(this.bus).subscribe(
            (busActualizado: BBusEnvioDTO) => {
              this.save.emit(busActualizado);
            },
            error => this.messageService.add({severity: 'error', summary: 'Error al guardar los cambios', detail: error, life: 3000})
          );
          this.messageService.add({ severity: 'success', summary: 'Guardado exitoso', detail: 'Los cambios han sido guardados.', life: 3000 });
        },
        reject: () => {
          this.messageService.add({severity: 'error', summary: 'Guardado cancelado', detail: 'No se guardaron los cambios.',  life: 3000});
        }
      })
    }
  }

  deleteBus(event: Event) {
    if (this.bus && this.bus.id) {

      // Verificar si el bus tiene rutas asignadas
      if (this.rutasAsignadas.length > 0) {
        this.messageService.add({
          severity: 'error',
          summary: 'No se puede eliminar',
          detail: 'No se puede eliminar el bus porque aún tiene rutas asignadas.',
          life: 3000
        });
        return;
      }

      this.confirmationService.confirm({
        target: event.target as EventTarget,
        message: '¿Estás seguro de que quieres eliminar este bus?',
        header: 'Confirmación de eliminación',
        icon: 'pi pi-info-circle',
        acceptButtonStyleClass:"p-button-danger p-button-text",
        rejectButtonStyleClass:"p-button-text p-button-text",
        acceptIcon:"none",
        rejectIcon:"none",
        accept: () => {
          this.gestionarBusesServices.eliminarBus(this.bus.id).subscribe(
            () => {
              this.close.emit();
              this.messageService.add({ severity: 'info', summary: 'Eliminación exitosa', detail: 'El bus ha sido eliminado.', life: 3000 });
            },
            error => this.messageService.add({severity: 'error', summary: 'Error al eliminar el bus', detail: error, life: 3000})
          );
        },
        reject: () => {
          this.messageService.add({severity: 'error', summary: 'Eliminación cancelada', detail: 'No se eliminó el bus.',  life: 3000});
        }
      });
    }
  }


  closeEdit() {
    this.close.emit();
  }
}
