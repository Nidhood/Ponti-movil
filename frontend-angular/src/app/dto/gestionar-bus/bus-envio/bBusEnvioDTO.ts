import {BRutaEnvioDTO} from './bRutaEnvioDTO';

export class BBusEnvioDTO {
  constructor(
    public id: string,
    public placa: string,
    public modelo: string,
    public diasSemana: string[],
    public rutas: BRutaEnvioDTO[]
  ) {}
}
