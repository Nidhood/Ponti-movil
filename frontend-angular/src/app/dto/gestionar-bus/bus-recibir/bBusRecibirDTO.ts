import {BCoductoresRecibirDTO} from './bCoductoresRecibirDTO';
import {BRutaEnvioDTO} from '../bus-envio/bRutaEnvioDTO';

export class BBusRecibirDTO {
  constructor(
    public id: string,
    public placa: string,
    public modelo: string,
    public diasSemana: string[],
    public rutas: BRutaEnvioDTO[],
    public conductores: BCoductoresRecibirDTO[]
  ) {}
}
