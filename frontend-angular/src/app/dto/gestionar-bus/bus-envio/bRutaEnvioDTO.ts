import {BHorarioEnvioDTO} from './bHorarioEnvioDTO';

export class BRutaEnvioDTO {
  constructor(
    public id: string,
    public codigo: string,
    public Horario: BHorarioEnvioDTO,
    public dentroBus: boolean
  ) {}
}
