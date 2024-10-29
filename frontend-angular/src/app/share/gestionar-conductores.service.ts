import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment.development';
import {CConductorRecibiendoDTO} from '../dto/gestionar-conductores/conductor-recibiendo/cConductorRecibiendoDTO';
import {CConductorEnviandoDTO} from '../dto/gestionar-conductores/conductor-enviando/cConductorEnviandoDTO';
import {CBusEnviandoDTO} from '../dto/gestionar-conductores/conductor-enviando/cBusEnviandoDTO';

@Injectable({
  providedIn: 'root'
})
export class GestionarConductoresService {

  constructor(private http: HttpClient) {}

  // Construimos el servicio para conseguir conductores:
  listaConductores(): Observable<CConductorRecibiendoDTO[]> {
    return this.http.get<CConductorRecibiendoDTO[]>(`${environment.SERVE_URL}/conductores`);
  }

  // Construimos el servicio para actualizar un conductor:
  actualizarConductor(conductor: CConductorEnviandoDTO): Observable<CConductorEnviandoDTO> {
    return this.http.post<CConductorEnviandoDTO>(`${environment.SERVE_URL}/conductores/${conductor.id}/actualizar`, conductor);
  }

  // Construimos el servicio para crear un conductor:
  crearConductor(conductor: CConductorEnviandoDTO): Observable<CConductorEnviandoDTO> {
    return this.http.post<CConductorEnviandoDTO>(`${environment.SERVE_URL}/conductores/crear`, conductor);
  }

  // Construimos el servicio para eliminar un conductor:
  eliminarConductor(id: string): Observable<void> {
    return this.http.delete<void>(`${environment.SERVE_URL}/conductores/${id}/eliminar`);
  }

  // construimos el servicio para conseguir todos los buses, sin tener en cuenta al conductor:
  obtenerBuses(): Observable<CBusEnviandoDTO[]>{
    return this.http.get<CBusEnviandoDTO[]>(`${environment.SERVE_URL}/conductores/buses`);
  }

  // Construimos el servicio para conseguir los conductores y buses:
  obtenerBusesPorConductor(idConductor: string): Observable<CBusEnviandoDTO[]>{
    return this.http.get<CBusEnviandoDTO[]>(`${environment.SERVE_URL}/conductores/${idConductor}/buses`);
  }
}
