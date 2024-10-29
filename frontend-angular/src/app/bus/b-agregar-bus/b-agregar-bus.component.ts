import {Component, EventEmitter, Input, Output} from '@angular/core';
import {AsyncPipe, NgIf} from '@angular/common';
import {Button} from 'primeng/button';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {MultiSelectModule} from 'primeng/multiselect';
import {PickListModule} from 'primeng/picklist';
import {ConfirmationService, MessageService, PrimeTemplate} from 'primeng/api';
import {RModuloRutaComponent} from '../../ruta/r-modulo-ruta/r-modulo-ruta.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {SkeletonModule} from 'primeng/skeleton';
import {ToastModule} from 'primeng/toast';
import {BehaviorSubject} from 'rxjs';
import {GestionarBusesService} from '../../share/gestionar-buses.service';
import {CConductorEnviandoDTO} from '../../dto/gestionar-conductores/conductor-enviando/cConductorEnviandoDTO';
import {BBusEnvioDTO} from '../../dto/gestionar-bus/bus-envio/bBusEnvioDTO';
import {BRutaEnvioDTO} from '../../dto/gestionar-bus/bus-envio/bRutaEnvioDTO';

interface DiaSemana {
  nombre: string;
  value: string;
}

@Component({
  selector: 'app-b-agregar-bus',
  standalone: true,
  imports: [
    AsyncPipe,
    Button,
    ConfirmDialogModule,
    MultiSelectModule,
    NgIf,
    PickListModule,
    PrimeTemplate,
    RModuloRutaComponent,
    ReactiveFormsModule,
    SkeletonModule,
    ToastModule,
    FormsModule
  ],
  templateUrl: './b-agregar-bus.component.html',
  styleUrl: './b-agregar-bus.component.css'
})
export class BAgregarBusComponent {
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

  horaInicioDate: Date = new Date();
  horaFinDate: Date = new Date();

  constructor(
    private gestionarBusesService: GestionarBusesService,
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
      this.horaInicioDate = this.stringToDate("00:00");
      this.horaFinDate = this.stringToDate("00:00");
    }
  }

  stringToDate(timeString: string): Date {
    const [hours, minutes] = timeString.split(':');
    const date = new Date();
    date.setHours(Number(hours));
    date.setMinutes(Number(minutes));
    return date;
  }

  cargarRutas() {
    this.loadingRutas$.next(true);
    if (this.bus) {
      this.gestionarBusesService.obtenerRutas().subscribe(
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

  createBus(event: Event) {
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
          this.gestionarBusesService.crearBus(this.bus).subscribe(
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

  closeEdit() {
    this.close.emit();
  }
}
