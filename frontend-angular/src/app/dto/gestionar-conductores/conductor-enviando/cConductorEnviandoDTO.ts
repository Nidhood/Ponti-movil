import {CBusEnviandoDTO} from './cBusEnviandoDTO';
import {CDireccionRecibiendoDTO} from '../conductor-recibiendo/cDireccionRecibiendoDTO';


export class CConductorEnviandoDTO {
  constructor(
    public id: string,
    public nombre: string,
    public apellido: string,
    public cedula: string,
    public telefono: string,
    public direccion: CDireccionRecibiendoDTO,
    public diasSemana: string[],
    public buses: CBusEnviandoDTO[],
  ) {}
}
