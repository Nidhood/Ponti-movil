import {DireccionDTO} from './direccion-dto';

export class EstacionDTO {
  constructor(
    public nombre: string,
    public direccion: DireccionDTO,
    public dentroRuta: boolean
  ) {}
}
