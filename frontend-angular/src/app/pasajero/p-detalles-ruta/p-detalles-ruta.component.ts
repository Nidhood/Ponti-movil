import {Component, Input} from '@angular/core';
import {RutaDto} from "../../dto/pasajero/ruta-dto";
import { ButtonModule } from 'primeng/button';
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-p-detalles-ruta',
  templateUrl: './p-detalles-ruta.component.html',
  styleUrls: ['./p-detalles-ruta.component.css'],
  standalone: true,
  imports: [ButtonModule, NgForOf]
})

export class PDetallesRutaComponent {
  @Input() ruta!: RutaDto; // La ruta es pasada desde el componente padre

  cerrarDetalle() {
    // Aquí puedes emitir un evento al componente padre para ocultar los detalles
    this.ruta = null as any; // Reseteamos la ruta para ocultar el modal
  }
}
