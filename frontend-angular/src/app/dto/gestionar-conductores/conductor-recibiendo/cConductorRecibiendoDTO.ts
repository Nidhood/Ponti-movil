import {CBusEnviandoDTO} from '../conductor-enviando/cBusEnviandoDTO';
import {BRutaEnvioDTO} from '../../gestionar-bus/bus-envio/bRutaEnvioDTO';
import {CDireccionRecibiendoDTO} from './cDireccionRecibiendoDTO';

export class CConductorRecibiendoDTO {
  constructor(
    public id: string,
    public nombre: string,
    public apellido: string,
    public cedula: string,
    public telefono: string,
    public direccion: CDireccionRecibiendoDTO,
    public diasSemana: string[],
    public buses: CBusEnviandoDTO[],
    public rutas: BRutaEnvioDTO[]
  ) {}
}
