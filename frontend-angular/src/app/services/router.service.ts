import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Definimos la estructura del JSON que recibimos
interface Horario {
  id: string;
  dia: string;
  horaInicio: string;
  horaFin: string;
}

interface Ruta {
  id: string;
  codigo: string;
  horario: Horario;
}

@Injectable({
  providedIn: 'root'
})
export class RouterService {

  // URL del servidor que devuelve las rutas
  private apiUrl = 'http://localhost:8080/rutas';

  constructor(private http: HttpClient) { }

  getRutas(): Observable<Ruta[]> {
    return this.http.get<Ruta[]>(this.apiUrl);
  }

  searchRutas(query: string): Observable<Ruta[]> {
    return this.http.get<Ruta[]>(`${this.apiUrl}?search=${query}`);
  }
}
