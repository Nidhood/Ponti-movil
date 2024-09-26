import {Component, OnInit} from '@angular/core';
import { PasajeroService } from '../../services/pasajero.service';
import { RutaDto } from "../../dto/pasajero/ruta-dto";
import {BehaviorSubject, map, Observable} from "rxjs";

@Component({
  selector: 'app-p-menu',
  templateUrl: './p-menu.component.html',
  styleUrls: ['./p-menu.component.css']
})

export class PMenuComponent implements OnInit {
  private rutasSubject = new BehaviorSubject<RutaDto[]>([]);
  rutas$: Observable<RutaDto[]> = this.rutasSubject.asObservable();
  selectedRuta: RutaDto | null = null;
  guiaVisible = false;

  constructor(private pasajeroService: PasajeroService) {}

  ngOnInit() {
    this.pasajeroService.listaRutas().subscribe(rutas => {
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
}
