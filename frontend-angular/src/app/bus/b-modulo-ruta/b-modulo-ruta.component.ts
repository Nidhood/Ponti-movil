import {Component, Input} from '@angular/core';
import {BRutaEnvioDTO} from '../../dto/gestionar-bus/bus-envio/bRutaEnvioDTO';

@Component({
  selector: 'app-b-modulo-ruta',
  standalone: true,
  imports: [],
  templateUrl: './b-modulo-ruta.component.html',
  styleUrl: './b-modulo-ruta.component.css'
})
export class BModuloRutaComponent {
  @Input() ruta: BRutaEnvioDTO | null = null;
}
