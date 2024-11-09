import { Component } from '@angular/core';
import {BehaviorSubject, finalize, map, Observable} from 'rxjs';
import {RRutaRecibidaDto} from '../../dto/gestionar-rutas/ruta-recibida/r-ruta-recibida-dto';
import {GestionarRutasService} from '../../share/gestionar-rutas.service';
import {ConfirmationService, MessageService} from 'primeng/api';
import {RRutaEnviadaDto} from '../../dto/gestionar-rutas/ruta-enviada/r-ruta-enviada-dto';
import {AnimationOptions, LottieComponent} from 'ngx-lottie';
import {AsyncPipe, NgForOf, NgIf, NgOptimizedImage} from '@angular/common';
import {RModuloRutaComponent} from '../r-modulo-ruta/r-modulo-ruta.component';
import {RDetallesRutaComponent} from '../r-detalles-ruta/r-detalles-ruta.component';
import {RBuscarRutaComponent} from '../r-buscar-ruta/r-buscar-ruta.component';
import {REditarRutaComponent} from '../r-editar-ruta/r-editar-ruta.component';
import {Button} from 'primeng/button';
import {RAgregarRutaComponent} from '../r-agregar-ruta/r-agregar-ruta.component';
import {RModuloAgregarRutaComponent} from '../r-modulo-agregar-ruta/r-modulo-agregar-ruta.component';
import {ToastModule} from 'primeng/toast';
import {RDetallesRutaPasajeroComponent} from '../r-detalles-ruta-pasajero/r-detalles-ruta-pasajero.component';

@Component({
  selector: 'app-r-menu-pasajero',
  standalone: true,
  imports: [
    NgIf,
    NgOptimizedImage,
    AsyncPipe,
    NgForOf,
    LottieComponent,
    RModuloRutaComponent,
    RDetallesRutaComponent,
    RBuscarRutaComponent,
    REditarRutaComponent,
    Button,
    RAgregarRutaComponent,
    RModuloAgregarRutaComponent,
    ToastModule,
    RDetallesRutaPasajeroComponent
  ],
  templateUrl: './r-menu-pasajero.component.html',
  styleUrl: './r-menu-pasajero.component.css',
  providers: [MessageService, ConfirmationService]
})
export class RMenuPasajeroComponent {
  private rutasSubject = new BehaviorSubject<RRutaRecibidaDto[]>([]);
  rutas$: Observable<RRutaRecibidaDto[]> = this.rutasSubject.asObservable();
  selectedRuta: RRutaRecibidaDto | null = null;
  editRuta: RRutaRecibidaDto | null = null;
  agregarRuta = false;
  guiaVisible = false;
  isLoading = true;

  constructor(
    private gestionarRutasService: GestionarRutasService,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.cargarRutas();
  }

  onSearch(searchTerm: string) {
    this.rutas$ = this.rutasSubject.pipe(
      map(rutas => rutas.filter(ruta =>
        ruta.codigo.toLowerCase().includes(searchTerm.toLowerCase()) ||
        ruta.estaciones.some(estacion =>
          estacion.nombre.toLowerCase().includes(searchTerm.toLowerCase())
        )
      ))
    );
  }

  seleccionarRuta(ruta: RRutaRecibidaDto) {
    this.selectedRuta = ruta;
  }

  cerrarDetalles() {
    this.selectedRuta = null;
  }

  mostrarGuia() {
    this.guiaVisible = true;
  }

  cerrarGuia() {
    this.guiaVisible = false;
  }

  cerrarModal() {
    this.selectedRuta = null;
    this.guiaVisible = false;
  }

  editarRuta() {
    this.editRuta = this.selectedRuta;
    this.selectedRuta = null;
  }

  cargarRutas() {
    this.isLoading = true;
    this.gestionarRutasService.listaRutas().pipe(
      finalize(() => this.isLoading = false)
    ).subscribe(
      (rutas: RRutaRecibidaDto[]) => {
        this.rutasSubject.next(rutas);
        this.messageService.add({severity: 'success', summary: 'Rutas cargadas', detail: 'Las rutas se han cargado correctamente'});

      },
      error => {
        this.messageService.add({severity: 'error', summary: 'Error al cargar rutas', detail: error});
      }
    );
  }

  options: AnimationOptions = {
    path: '/assets/animations/loading.json'
  };
}
