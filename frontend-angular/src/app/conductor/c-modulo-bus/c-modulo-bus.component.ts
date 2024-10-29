import {Component, Input} from '@angular/core';
import {CBusEnviandoDTO} from '../../dto/gestionar-conductores/conductor-enviando/cBusEnviandoDTO';

@Component({
  selector: 'app-c-modulo-bus',
  standalone: true,
  imports: [],
  templateUrl: './c-modulo-bus.component.html',
  styleUrl: './c-modulo-bus.component.css'
})
export class CModuloBusComponent {
  @Input() bus: CBusEnviandoDTO | null = null;

}
