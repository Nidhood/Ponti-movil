import {Component, OnInit} from '@angular/core';
import {RouterService} from "../../services/router.service";

@Component({
  selector: 'app-p-menu',
  templateUrl: './p-menu.component.html',
  styleUrls: ['./p-menu.component.css']
})

export class PMenuComponent implements OnInit {

  rutas: any[] = [];  // Aquí se almacenarán las rutas obtenidas

  constructor(private routerService: RouterService) { }

  ngOnInit(): void {
    this.getRutas();
  }

  getRutas(): void {
    this.routerService.getRutas().subscribe(
      (data) => {
        this.rutas = data;
      },
      (error) => {
        console.error('Error al obtener rutas:', error);
      }
    );
  }
}

