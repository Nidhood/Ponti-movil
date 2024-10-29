import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Button} from 'primeng/button';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-c-detalles-conductor',
  standalone: true,
  imports: [
    Button,
    NgForOf
  ],
  templateUrl: './c-detalles-conductor.component.html',
  styleUrl: './c-detalles-conductor.component.css'
})
export class CDetallesConductorComponent {
  @Input() conductor: any;
  @Output() close = new EventEmitter<void>();
  @Output() edit = new EventEmitter<void>();
  hover: boolean = false;

  constructor() {}

  cerrarDetalle() {
    this.close.emit();
  }

  editarConductor() {
    this.edit.emit();
  }
}
