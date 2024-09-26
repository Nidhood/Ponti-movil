import {Component, EventEmitter, Input, Output} from '@angular/core';
import {RutaDto} from "../../dto/pasajero/ruta-dto";

@Component({
  selector: 'app-p-modulo-ruta',
  templateUrl: './p-modulo-ruta.component.html',
  styleUrls: ['./p-modulo-ruta.component.css']
})

export class PModuloRutaComponent {
  @Input() ruta!: RutaDto;
  @Output() rutaSelected = new EventEmitter<RutaDto>();

  seleccionarRuta() {
    this.rutaSelected.emit(this.ruta);
  }
}
