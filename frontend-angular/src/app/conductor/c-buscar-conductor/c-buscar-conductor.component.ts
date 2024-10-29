import {Component, EventEmitter, Output} from '@angular/core';
import {AnimationOptions, LottieComponent} from "ngx-lottie";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AnimationItem} from 'lottie-web';

@Component({
  selector: 'app-c-buscar-conductor',
  standalone: true,
  imports: [
    LottieComponent,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './c-buscar-conductor.component.html',
  styleUrl: './c-buscar-conductor.component.css'
})

export class CBuscarConductorComponent {
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
