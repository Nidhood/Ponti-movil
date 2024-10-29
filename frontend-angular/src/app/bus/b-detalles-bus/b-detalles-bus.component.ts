import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgForOf} from '@angular/common';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-b-detalles-bus',
  standalone: true,
  imports: [
    NgForOf,
    ButtonModule
  ],
  templateUrl: './b-detalles-bus.component.html',
  styleUrl: './b-detalles-bus.component.css'
})
export class BDetallesBusComponent {
  @Input() bus: any;
  @Output() close = new EventEmitter<void>();
  @Output() edit = new EventEmitter<void>();
  hover: boolean = false;

  constructor() {}

  cerrarDetalle() {
    this.close.emit();
  }

  editarBus() {
    this.edit.emit();
  }
}
