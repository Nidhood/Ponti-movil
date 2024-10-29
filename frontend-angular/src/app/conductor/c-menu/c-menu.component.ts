import { Component } from '@angular/core';
import {AsyncPipe, NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {Button} from "primeng/button";
import {AnimationOptions, LottieComponent} from "ngx-lottie";
import {RAgregarRutaComponent} from "../../ruta/r-agregar-ruta/r-agregar-ruta.component";
import {CBuscarConductorComponent} from "../c-buscar-conductor/c-buscar-conductor.component";
import {RDetallesRutaComponent} from "../../ruta/r-detalles-ruta/r-detalles-ruta.component";
import {REditarRutaComponent} from "../../ruta/r-editar-ruta/r-editar-ruta.component";
import {RModuloAgregarRutaComponent} from "../../ruta/r-modulo-agregar-ruta/r-modulo-agregar-ruta.component";
import {RModuloRutaComponent} from "../../ruta/r-modulo-ruta/r-modulo-ruta.component";
import {ToastModule} from "primeng/toast";
import {BehaviorSubject, finalize, map, Observable} from 'rxjs';
import {ConfirmationService, MessageService} from 'primeng/api';
import {BAgregarBusComponent} from '../../bus/b-agregar-bus/b-agregar-bus.component';
import {BBuscarBusComponent} from '../../bus/b-buscar-bus/b-buscar-bus.component';
import {BDetallesBusComponent} from '../../bus/b-detalles-bus/b-detalles-bus.component';
import {BEditarBusComponent} from '../../bus/b-editar-bus/b-editar-bus.component';
import {BModuloAgregarBusComponent} from '../../bus/b-modulo-agregar-bus/b-modulo-agregar-bus.component';
import {BModuloBusComponent} from '../../bus/b-modulo-bus/b-modulo-bus.component';
import {RBuscarRutaComponent} from '../../ruta/r-buscar-ruta/r-buscar-ruta.component';
import {CConductorRecibiendoDTO} from '../../dto/gestionar-conductores/conductor-recibiendo/cConductorRecibiendoDTO';
import {GestionarConductoresService} from '../../share/gestionar-conductores.service';
import {CModuloConductorComponent} from '../c-modulo-conductor/c-modulo-conductor.component';
import {CModuloAgregarConductorComponent} from '../c-modulo-agregar-conductor/c-modulo-agregar-conductor.component';
import {CConductorEnviandoDTO} from '../../dto/gestionar-conductores/conductor-enviando/cConductorEnviandoDTO';
import {CDetallesConductorComponent} from '../c-detalles-conductor/c-detalles-conductor.component';
import {CEditarConductorComponent} from '../c-editar-conductor/c-editar-conductor.component';
import {CAgregarConductorComponent} from '../c-agregar-conductor/c-agregar-conductor.component';

@Component({
  selector: 'app-c-menu',
  standalone: true,
  imports: [
    AsyncPipe,
    Button,
    LottieComponent,
    NgForOf,
    NgIf,
    NgOptimizedImage,
    RAgregarRutaComponent,
    RDetallesRutaComponent,
    REditarRutaComponent,
    RModuloAgregarRutaComponent,
    RModuloRutaComponent,
    ToastModule,
    CBuscarConductorComponent,
    BAgregarBusComponent,
    BBuscarBusComponent,
    BDetallesBusComponent,
    BEditarBusComponent,
    BModuloAgregarBusComponent,
    BModuloBusComponent,
    RBuscarRutaComponent,
    CModuloConductorComponent,
    CModuloAgregarConductorComponent,
    CDetallesConductorComponent,
    CEditarConductorComponent,
    CAgregarConductorComponent
  ],
  templateUrl: './c-menu.component.html',
  styleUrl: './c-menu.component.css',
  providers: [MessageService, ConfirmationService]
})
export class CMenuComponent {
  private conductorSubject = new BehaviorSubject<CConductorRecibiendoDTO[]>([]);
  conductores$: Observable<CConductorRecibiendoDTO[]> = this.conductorSubject.asObservable();
  selectedConductor: CConductorRecibiendoDTO | null = null;
  editConductor: CConductorRecibiendoDTO | null = null;
  agregarConductor = false;
  isLoading = true;

  constructor(
    private gestionarConductoresService: GestionarConductoresService,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.cargarBuses();
  }

  onSearch(searchTerm: string) {
    this.conductores$ = this.conductorSubject.pipe(
      map(conductores => conductores.filter(conductor =>
        conductor.nombre.toLowerCase().includes(searchTerm.toLowerCase()) ||
        conductor.apellido.toLowerCase().includes(searchTerm.toLowerCase()) ||
        conductor.cedula.toLowerCase().includes(searchTerm.toLowerCase()) ||
        conductor.telefono.toLowerCase().includes(searchTerm.toLowerCase())
      ))
    );
  }

  seleccionarBus(ruta: CConductorRecibiendoDTO) {
    this.selectedConductor = ruta;
  }

  cerrarDetalles() {
    this.selectedConductor = null;
  }

  cerrarModal() {
    this.selectedConductor = null;
  }

  editarConductor() {
    this.editConductor = this.selectedConductor;
    this.selectedConductor = null;
  }

  cerrarEditar() {
    this.editConductor = null;
  }

  cerrarAgregar() {
    this.agregarConductor = false;
  }

  cargarBuses() {
    this.isLoading = true;
    this.gestionarConductoresService.listaConductores().pipe(
      finalize(() => this.isLoading = false)
    ).subscribe(
      (conductores: CConductorRecibiendoDTO[]) => {
        this.conductorSubject.next(conductores);
        this.messageService.add({severity: 'success', summary: 'Buses cargados', detail: 'Los buses han sido cargados.'});

      },
      error => {
        this.messageService.add({severity: 'error', summary: 'Error al cargar buses', detail: error});
      }
    );
  }

  guardarNuevoBus($event: CConductorEnviandoDTO) {
    this.gestionarConductoresService.crearConductor($event).subscribe(
      () => {
        this.cargarBuses();
        this.cerrarAgregar();
      },
      error => console.error('Error al guardar la nueva ruta:', error)
    );
  }

  guardarCambiosBus($event: CConductorEnviandoDTO) {
    this.gestionarConductoresService.actualizarConductor($event).subscribe(
      () => {
        this.cargarBuses()
        this.cerrarEditar();
      },
      error => console.error('Error al actualizar la ruta:', error)
    );
  }

  abrirFormulario() {
    this.agregarConductor = true;
  }

  options: AnimationOptions = {
    path: '/assets/animations/loading.json'
  };
}
