import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-p-buscar-ruta',
  templateUrl: './p-buscar-ruta.component.html',
  styleUrls: ['./p-buscar-ruta.component.css']
})

export class PBuscarRutaComponent {
  searchTerm = '';
  @Output() search = new EventEmitter<string>();

  onSearch() {
    this.search.emit(this.searchTerm);
  }
}
