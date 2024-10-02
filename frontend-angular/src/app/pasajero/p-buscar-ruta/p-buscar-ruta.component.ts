import {Component, EventEmitter, Output} from '@angular/core';
import {AnimationOptions, LottieComponent} from "ngx-lottie";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-p-buscar-ruta',
  standalone: true,
  templateUrl: './p-buscar-ruta.component.html',
  styleUrls: ['./p-buscar-ruta.component.css'],
  imports: [LottieComponent, FormsModule]
})

export class PBuscarRutaComponent {
  searchTerm = '';
  @Output() search = new EventEmitter<string>();

  onSearch() {
    this.search.emit(this.searchTerm);
  }

  options: AnimationOptions = {
    path: '/assets/animations/search.json'
  };
}
