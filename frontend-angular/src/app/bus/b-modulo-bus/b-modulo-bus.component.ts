import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BBusRecibirDTO} from '../../dto/gestionar-bus/bus-recibir/bBusRecibirDTO';


@Component({
  selector: 'app-b-modulo-bus',
  standalone: true,
  imports: [],
  templateUrl: './b-modulo-bus.component.html',
  styleUrl: './b-modulo-bus.component.css'
})
export class BModuloBusComponent {
  @Input() bus!: BBusRecibirDTO;
  @Output() busSelected = new EventEmitter<BBusRecibirDTO>();
  seleccionarBus() {
    this.busSelected.emit(this.bus);
  }
}
