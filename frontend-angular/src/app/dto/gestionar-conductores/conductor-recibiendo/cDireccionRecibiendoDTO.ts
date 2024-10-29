export class CDireccionRecibiendoDTO {
  constructor(
    public id: string,
    public tipoVia: string,
    public numeroVia: string,
    public numero: string,
    public localidad: string,
    public barrio: string
  ) {}
}
