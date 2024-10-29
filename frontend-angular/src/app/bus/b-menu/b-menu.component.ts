import { Component } from '@angular/core';
import {AsyncPipe, NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import { ButtonModule } from 'primeng/button';
import {AnimationOptions, LottieComponent} from "ngx-lottie";
import {RAgregarRutaComponent} from "../../ruta/r-agregar-ruta/r-agregar-ruta.component";
import {RBuscarRutaComponent} from "../../ruta/r-buscar-ruta/r-buscar-ruta.component";
import {RDetallesRutaComponent} from "../../ruta/r-detalles-ruta/r-detalles-ruta.component";
import {REditarRutaComponent} from "../../ruta/r-editar-ruta/r-editar-ruta.component";
import {RModuloAgregarRutaComponent} from "../../ruta/r-modulo-agregar-ruta/r-modulo-agregar-ruta.component";
import {RModuloRutaComponent} from "../../ruta/r-modulo-ruta/r-modulo-ruta.component";
import {ToastModule} from "primeng/toast";
import {BehaviorSubject, finalize, map, Observable} from 'rxjs';
import {ConfirmationService, MessageService} from 'primeng/api';
import {GestionarBusesService} from '../../share/gestionar-buses.service';
import {BModuloBusComponent} from '../b-modulo-bus/b-modulo-bus.component';
import {BModuloAgregarBusComponent} from '../b-modulo-agregar-bus/b-modulo-agregar-bus.component';
import {BBuscarBusComponent} from '../b-buscar-bus/b-buscar-bus.component';
import {BBusRecibirDTO} from '../../dto/gestionar-bus/bus-recibir/bBusRecibirDTO';
import {BDetallesBusComponent} from '../b-detalles-bus/b-detalles-bus.component';
import {BEditarBusComponent} from '../b-editar-bus/b-editar-bus.component';
import {BAgregarBusComponent} from '../b-agregar-bus/b-agregar-bus.component';
import {BBusEnvioDTO} from '../../dto/gestionar-bus/bus-envio/bBusEnvioDTO';

@Component({
  selector: 'app-b-menu',
  standalone: true,
  imports: [
    AsyncPipe,
    ButtonModule,
    LottieComponent,
    NgForOf,
    NgIf,
    NgOptimizedImage,
    RAgregarRutaComponent,
    RBuscarRutaComponent,
    RDetallesRutaComponent,
    REditarRutaComponent,
    RModuloAgregarRutaComponent,
    RModuloRutaComponent,
    ToastModule,
    BModuloBusComponent,
    BModuloAgregarBusComponent,
    BBuscarBusComponent,
    BDetallesBusComponent,
    BEditarBusComponent,
    BAgregarBusComponent
  ],
  templateUrl: './b-menu.component.html',
  styleUrl: './b-menu.component.css',
  providers: [MessageService, ConfirmationService]
})
export class BMenuComponent {
  private busesSubject = new BehaviorSubject<BBusRecibirDTO[]>([]);
  buses$: Observable<BBusRecibirDTO[]> = this.busesSubject.asObservable();
  selectedBus: BBusRecibirDTO | null = null;
  editBus: BBusRecibirDTO | null = null;
  agregarBus = false;
  isLoading = true;

  constructor(
    private gestionarBusesService: GestionarBusesService,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.cargarBuses();
  }

  onSearch(searchTerm: string) {
    this.buses$ = this.busesSubject.pipe(
      map(buses => buses.filter(bus =>
        bus.modelo.toLowerCase().includes(searchTerm.toLowerCase()) ||
        bus.placa.toLowerCase().includes(searchTerm.toLowerCase())
      ))
    );
  }

  seleccionarBus(ruta: BBusRecibirDTO) {
    this.selectedBus = ruta;
  }

  cerrarDetalles() {
    this.selectedBus = null;
  }

  cerrarModal() {
    this.selectedBus = null;
  }

  editarBus() {
    this.editBus = this.selectedBus;
    this.selectedBus = null;
  }

  cerrarEditar() {
    this.editBus = null;
  }

  cerrarAgregar() {
    this.agregarBus = false;
  }

  cargarBuses() {
    this.isLoading = true;
    this.gestionarBusesService.listaBuses().pipe(
      finalize(() => this.isLoading = false)
    ).subscribe(
      (buses: BBusRecibirDTO[]) => {
        this.busesSubject.next(buses);
        this.messageService.add({severity: 'success', summary: 'Buses cargados', detail: 'Los buses han sido cargados.'});

      },
      error => {
        this.messageService.add({severity: 'error', summary: 'Error al cargar buses', detail: error});
      }
    );
  }

  guardarNuevoBus($event: BBusEnvioDTO) {
    this.gestionarBusesService.crearBus($event).subscribe(
      () => {
        this.cargarBuses();
        this.cerrarAgregar();
      },
      error => console.error('Error al guardar la nueva ruta:', error)
    );
  }

  guardarCambiosBus($event: BBusEnvioDTO) {
    this.gestionarBusesService.actualizarBus($event).subscribe(
      () => {
        this.cargarBuses()
        this.cerrarEditar();
      },
      error => console.error('Error al actualizar la ruta:', error)
    );
  }

  abrirFormulario() {
    this.agregarBus = true;
  }

  options: AnimationOptions = {
    path: '/assets/animations/loading.json'
  };
}
