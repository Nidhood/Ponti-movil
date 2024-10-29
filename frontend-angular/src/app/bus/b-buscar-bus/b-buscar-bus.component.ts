import {Component, EventEmitter, Output} from '@angular/core';
import {AnimationOptions, LottieComponent} from "ngx-lottie";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AnimationItem} from 'lottie-web';

@Component({
  selector: 'app-b-buscar-bus',
  standalone: true,
  imports: [
    LottieComponent,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './b-buscar-bus.component.html',
  styleUrl: './b-buscar-bus.component.css'
})
export class BBuscarBusComponent {
  searchTerm = '';
  private animationItem: AnimationItem | undefined;
  @Output() search = new EventEmitter<string>();
  onSearch() {
    this.search.emit(this.searchTerm);
  }

  options: AnimationOptions = {
    path: '/assets/animations/search.json',
    autoplay: false,
    loop: false
  };

  animationCreated(animationItem: AnimationItem): void {
    this.animationItem = animationItem;
  }

  startAnimation(): void {
    if (this.animationItem) {
      this.animationItem.goToAndPlay(0);
    }
  }

  completeAnimation(): void {
    if (this.animationItem) {
      this.animationItem.setSpeed(2); // Aumenta la velocidad para completar más rápido
      this.animationItem.play();
    }
  }
}
