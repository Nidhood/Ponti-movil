import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Button} from "primeng/button";
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {CModuloBusComponent} from '../c-modulo-bus/c-modulo-bus.component';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {MultiSelectModule} from 'primeng/multiselect';
import {PaginatorModule} from 'primeng/paginator';
import {PickListModule} from 'primeng/picklist';
import {ConfirmationService, MessageService, PrimeTemplate} from 'primeng/api';
import {SkeletonModule} from 'primeng/skeleton';
import {ToastModule} from 'primeng/toast';
import {CConductorEnviandoDTO} from '../../dto/gestionar-conductores/conductor-enviando/cConductorEnviandoDTO';
import {CDireccionRecibiendoDTO} from '../../dto/gestionar-conductores/conductor-recibiendo/cDireccionRecibiendoDTO';
import {CBusEnviandoDTO} from '../../dto/gestionar-conductores/conductor-enviando/cBusEnviandoDTO';
import {BehaviorSubject} from 'rxjs';
import {GestionarConductoresService} from '../../share/gestionar-conductores.service';

interface DiaSemana {
  nombre: string;
  value: string;
}

@Component({
  selector: 'app-c-agregar-conductor',
  standalone: true,
  imports: [
    Button,
    NgForOf,
    AsyncPipe,
    CModuloBusComponent,
    ConfirmDialogModule,
    MultiSelectModule,
    NgIf,
    PaginatorModule,
    PickListModule,
    PrimeTemplate,
    SkeletonModule,
    ToastModule
  ],
  templateUrl: './c-agregar-conductor.component.html',
  styleUrl: './c-agregar-conductor.component.css'
})
export class CAgregarConductorComponent {
  @Input() conductor: CConductorEnviandoDTO = new CConductorEnviandoDTO(
    '', // id
    '', // nombre
    '', // apellido
    '', // cedula
    '', // telefono
    new CDireccionRecibiendoDTO('', '', '', '', '', ''), // direccion
    [], // días de la semana
    [] // buses
  );
  @Output() close = new EventEmitter<void>();
  @Output() save = new EventEmitter<CConductorEnviandoDTO>();

  busesAsignados: CBusEnviandoDTO[] = [];
  busesNoAsignados: CBusEnviandoDTO[] = [];

  diasOptions!: DiaSemana[];
  selectedDias!: DiaSemana[];

  horaInicioDate: Date = new Date();
  horaFinDate: Date = new Date();

  public loadingBuses$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(true);

  skeletonItems: any[] = Array(5).fill({});

  constructor(
    private gestionarConductoresService: GestionarConductoresService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
  ) {}

  ngOnInit() {
    if (this.conductor) {
      this.diasOptions = [
        { nombre: 'Lunes', value: 'Lunes' },
        { nombre: 'Martes', value: 'Martes' },
        { nombre: 'Miércoles', value: 'Miercoles' },
        { nombre: 'Jueves', value: 'Jueves' },
        { nombre: 'Viernes', value: 'Viernes' },
        { nombre: 'Sábado', value: 'Sabado' },
        { nombre: 'Domingo', value: 'Domingo' }
      ];
      this.cargarBuses();
      this.horaInicioDate = this.stringToDate("00:00");
      this.horaFinDate = this.stringToDate("00:00");    }
  }

  stringToDate(timeString: string): Date {
    const [hours, minutes] = timeString.split(':');
    const date = new Date();
    date.setHours(Number(hours));
    date.setMinutes(Number(minutes));
    return date;
  }

  cargarBuses() {
    this.loadingBuses$.next(true);
    if (this.conductor) {
      this.gestionarConductoresService.obtenerBuses().subscribe(
        (buses: CBusEnviandoDTO[]) => {
          this.busesAsignados = buses.filter(bus => bus.dentroConductor);
          this.busesNoAsignados = buses.filter(bus => !bus.dentroConductor);
          this.loadingBuses$.next(false);
          this.actualizarEstadoBuses();
          this.messageService.add({ severity: 'success', summary: 'Buses cargados', detail: 'Los buses han sido cargados.', life: 3000 });
        },
        error => {
          this.loadingBuses$.next(false);
          this.messageService.add({ severity: 'error', summary: 'Error al cargar buses', detail: error, life: 3000 });
        }
      );
    } else {
      this.loadingBuses$.next(false);
      this.messageService.add({ severity: 'error', summary: 'Error al cargar buses', detail: 'No se pudo cargar los buses.', life: 3000 });
    }
  }


  onMoveToTarget(event: any) {
    this.actualizarEstadoBuses();
  }

  onMoveToSource(event: any) {
    this.actualizarEstadoBuses();
  }

  actualizarEstadoBuses() {
    this.busesAsignados.forEach(conductor => {
      conductor.dentroConductor = true;
    });

    this.busesNoAsignados.forEach(conductor => {
      conductor.dentroConductor = false;
    });
  }

  actualizarDias() {
    this.conductor.diasSemana = this.selectedDias.map(dia => dia.value);
  }

  createConductor(event: Event) {
    if (this.conductor) {
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
          this.conductor.buses = this.busesAsignados;
          this.gestionarConductoresService.crearConductor(this.conductor).subscribe(
            (busActualizado: CConductorEnviandoDTO) => {
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
