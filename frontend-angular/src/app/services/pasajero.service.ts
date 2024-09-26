import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.development";
import {Observable} from "rxjs";
import {RutaDto} from "../dto/pasajero/ruta-dto";

@Injectable({
  providedIn: 'root'
})

export class PasajeroService {

  constructor(private http: HttpClient) { }

  // Construimos el servicio para conseguir rutas:
  listaRutas(): Observable<RutaDto[]> {
    return this.http.get<RutaDto[]>(`${environment.SERVE_URL}/rutas/diasSemana`);
  }
}
