import {Component, EventEmitter, Input, Output} from '@angular/core';
import {AsyncPipe, NgIf} from "@angular/common";
import {Button} from "primeng/button";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {MultiSelectModule} from "primeng/multiselect";
import {PaginatorModule} from "primeng/paginator";
import {PickListModule} from "primeng/picklist";
import {ConfirmationService, MessageService, PrimeTemplate} from "primeng/api";
import {RModuloRutaComponent} from "../../ruta/r-modulo-ruta/r-modulo-ruta.component";
import {SkeletonModule} from "primeng/skeleton";
import {ToastModule} from "primeng/toast";
import {BehaviorSubject} from 'rxjs';
import {GestionarConductoresService} from '../../share/gestionar-conductores.service';
import {CBusEnviandoDTO} from '../../dto/gestionar-conductores/conductor-enviando/cBusEnviandoDTO';
import {CConductorEnviandoDTO} from '../../dto/gestionar-conductores/conductor-enviando/cConductorEnviandoDTO';
import {CDireccionRecibiendoDTO} from '../../dto/gestionar-conductores/conductor-recibiendo/cDireccionRecibiendoDTO';
import {CModuloBusComponent} from '../c-modulo-bus/c-modulo-bus.component';

interface DiaSemana {
  nombre: string;
  value: string;
}

@Component({
  selector: 'app-c-editar-conductor',
  standalone: true,
  imports: [
    AsyncPipe,
    Button,
    ConfirmDialogModule,
    MultiSelectModule,
    NgIf,
    PaginatorModule,
    PickListModule,
    PrimeTemplate,
    RModuloRutaComponent,
    SkeletonModule,
    ToastModule,
    CModuloBusComponent
  ],
  templateUrl: './c-editar-conductor.component.html',
  styleUrl: './c-editar-conductor.component.css'
})

export class CEditarConductorComponent {
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
      this.selectedDias = this.diasOptions.filter(option => this.conductor.diasSemana.includes(option.value));
    }
  }

  cargarBuses() {
    this.loadingBuses$.next(true);
    if (this.conductor && this.conductor.id) {
      this.gestionarConductoresService.obtenerBusesPorConductor(this.conductor.id).subscribe(
        (buses: CBusEnviandoDTO[]) => {
          // Asignar los buses recibidos a las variables del componente
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

  saveChanges(event: Event) {
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
          this.gestionarConductoresService.actualizarConductor(this.conductor).subscribe(
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

  deleteConductor(event: Event) {
    if (this.conductor && this.conductor.id) {
      // Verificar si el conductor tiene buses asignados
      if (this.busesAsignados.length > 0) {
        this.messageService.add({
          severity: 'error',
          summary: 'No se puede eliminar',
          detail: 'No se puede eliminar el conductor porque aún tiene buses asignados.',
          life: 3000
        });
        return;
      }

      this.confirmationService.confirm({
        target: event.target as EventTarget,
        message: '¿Estás seguro de que quieres eliminar este conductor?',
        header: 'Confirmación de eliminación',
        icon: 'pi pi-info-circle',
        acceptButtonStyleClass:"p-button-danger p-button-text",
        rejectButtonStyleClass:"p-button-text p-button-text",
        acceptIcon:"none",
        rejectIcon:"none",
        accept: () => {
          this.gestionarConductoresService.eliminarConductor(this.conductor.id).subscribe(
            () => {
              this.close.emit();
              this.messageService.add({ severity: 'info', summary: 'Eliminación exitosa', detail: 'El conductor ha sido eliminado.', life: 3000 });
            },
            error => this.messageService.add({severity: 'error', summary: 'Error al eliminar el conductor', detail: error, life: 3000})
          );
        },
        reject: () => {
          this.messageService.add({severity: 'error', summary: 'Eliminación cancelada', detail: 'No se eliminó el conductor.',  life: 3000});
        }
      });
    }
  }

  closeEdit() {
    this.close.emit();
  }
}
