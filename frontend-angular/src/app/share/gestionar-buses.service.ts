import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment.development';
import {BBusRecibirDTO} from '../dto/gestionar-bus/bus-recibir/bBusRecibirDTO';
import {BBusEnvioDTO} from '../dto/gestionar-bus/bus-envio/bBusEnvioDTO';
import {BRutaEnvioDTO} from '../dto/gestionar-bus/bus-envio/bRutaEnvioDTO';

@Injectable({
  providedIn: 'root'
})
export class GestionarBusesService {

  constructor(private http: HttpClient) { }

  // Construimos el servicio para conseguir buses:
  listaBuses(): Observable<BBusRecibirDTO[]> {
    return this.http.get<BBusRecibirDTO[]>(`${environment.SERVE_URL}/buses`);
  }

  // Construimos el servicio para conseguir todos los buses:
  obtenerRutas(): Observable<BRutaEnvioDTO[]> {
    return this.http.get<BRutaEnvioDTO[]>(`${environment.SERVE_URL}/buses/rutas`);
  }

  // Construimos el servicio para conseguir los buses y rutas:
  obtenerRutasPorBuses(idBus: string): Observable<BRutaEnvioDTO[]> {
    return this.http.get<BRutaEnvioDTO[]>(`${environment.SERVE_URL}/buses/${idBus}/rutas`);
  }

  // Construimos el servicio para crear un bus:
  crearBus(bus: any): Observable<BBusEnvioDTO> {
    return this.http.post<BBusEnvioDTO>(`${environment.SERVE_URL}/buses/crear`, bus);
  }

  // Construimos el servicio para actualizar un bus:
  actualizarBus(bus: any): Observable<BBusEnvioDTO> {
    return this.http.post<BBusEnvioDTO>(`${environment.SERVE_URL}/buses/${bus.id}/actualizar`, bus);
  }

  // Construimos el servicio para eliminar un bus:
  eliminarBus(id: string): Observable<void> {
    return this.http.delete<void>(`${environment.SERVE_URL}/buses/${id}/eliminar`);
  }
}
