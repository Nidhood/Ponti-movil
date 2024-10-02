import {Component, OnInit} from '@angular/core';
import { PasajeroService } from '../../services/pasajero.service';
import { RutaDto } from "../../dto/pasajero/ruta-dto";
import {BehaviorSubject, finalize, map, Observable} from "rxjs";
import {AnimationOptions, LottieComponent} from "ngx-lottie";
import {PBuscarRutaComponent} from "../p-buscar-ruta/p-buscar-ruta.component";
import {PModuloRutaComponent} from "../p-modulo-ruta/p-modulo-ruta.component";
import {PDetallesRutaComponent} from "../p-detalles-ruta/p-detalles-ruta.component";
import {AsyncPipe, NgForOf, NgIf, NgOptimizedImage} from "@angular/common";

@Component({
  selector: 'app-p-menu',
  standalone: true,
  templateUrl: './p-menu.component.html',
  styleUrls: ['./p-menu.component.css'],
  imports: [
    PBuscarRutaComponent,
    LottieComponent,
    PModuloRutaComponent,
    PDetallesRutaComponent,
    NgIf,
    NgForOf,
    NgOptimizedImage,
    AsyncPipe
  ]
})

export class PMenuComponent implements OnInit {
  private rutasSubject = new BehaviorSubject<RutaDto[]>([]);
  rutas$: Observable<RutaDto[]> = this.rutasSubject.asObservable();
  selectedRuta: RutaDto | null = null;
  guiaVisible = false;
  isLoading = true;

  constructor(private pasajeroService: PasajeroService) {}

  ngOnInit() {
    this.pasajeroService.listaRutas().pipe(
      finalize(() => this.isLoading = false)
    ).subscribe(rutas => {
      this.rutasSubject.next(rutas);
    });
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

  seleccionarRuta(ruta: RutaDto) {
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

  options: AnimationOptions = {
    path: '/assets/animations/loading.json'
  };
}
