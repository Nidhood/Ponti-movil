import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Button} from "primeng/button";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {RouterLink} from '@angular/router';
import {CalendarModule} from 'primeng/calendar';
import {FormsModule} from '@angular/forms';
import {ChipsModule} from 'primeng/chips';
import {PickListModule} from 'primeng/picklist';
import {TimelineModule} from 'primeng/timeline';

@Component({
  selector: 'app-r-detalles-ruta-pasajero',
  standalone: true,
  imports: [
    NgForOf,
    Button,
    RouterLink,
    NgIf,
    NgClass,
    CalendarModule,
    FormsModule,
    ChipsModule,
    PickListModule,
    TimelineModule
  ],
  templateUrl: './r-detalles-ruta-pasajero.component.html',
  styleUrl: './r-detalles-ruta-pasajero.component.css'
})
export class RDetallesRutaPasajeroComponent {
  @Input() ruta: any;
  @Output() close = new EventEmitter<void>();
  @Output() edit = new EventEmitter<void>();
  hover: boolean = false;
  horaInicioDate: Date = new Date();
  horaFinDate: Date = new Date();

  constructor() {}

  ngOnInit() {
    if (this.ruta) {
      this.horaInicioDate = this.stringToDate(this.ruta.horario.horaInicio);
      this.horaFinDate = this.stringToDate(this.ruta.horario.horaFin);
    }
  }

  formatDate(date: Date): string {
    const hours = String(date.getHours()).padStart(2, '0'); // Obtener las horas
    const minutes = String(date.getMinutes()).padStart(2, '0'); // Obtener los minutos
    return `${hours}:${minutes}`; // Devolver en formato 'hh:mm'
  }


  stringToDate(timeString: string): Date {
    const [hours, minutes] = timeString.split(':');
    const date = new Date();
    date.setHours(Number(hours));
    date.setMinutes(Number(minutes));
    return date;
  }

  cerrarDetalle() {
    this.close.emit();
  }
}
