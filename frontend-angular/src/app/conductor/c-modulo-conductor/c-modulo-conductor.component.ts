import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CConductorRecibiendoDTO} from '../../dto/gestionar-conductores/conductor-recibiendo/cConductorRecibiendoDTO';

@Component({
  selector: 'app-c-modulo-conductor',
  standalone: true,
  imports: [],
  templateUrl: './c-modulo-conductor.component.html',
  styleUrl: './c-modulo-conductor.component.css'
})
export class CModuloConductorComponent {
  @Input() conductor!: CConductorRecibiendoDTO;
  @Output() conductorSelected = new EventEmitter<CConductorRecibiendoDTO>();
  seleccionarConductor() {
    this.conductorSelected.emit(this.conductor);
  }
}
