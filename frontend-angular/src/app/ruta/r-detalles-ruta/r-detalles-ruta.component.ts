import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {Button} from 'primeng/button';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-r-detalles-ruta',
  standalone: true,
  imports: [
    NgForOf,
    Button,
    RouterLink,
    NgIf,
    NgClass
  ],
  templateUrl: './r-detalles-ruta.component.html',
  styleUrl: './r-detalles-ruta.component.css'
})
export class RDetallesRutaComponent {
  @Input() ruta: any;
  @Output() close = new EventEmitter<void>();
  hover: boolean = false;

  constructor() {}

  cerrarDetalle() {
    this.close.emit();
  }

  onMouseEnter() {
    this.hover = true;
  }

  onMouseLeave() {
    this.hover = false;
  }
}
