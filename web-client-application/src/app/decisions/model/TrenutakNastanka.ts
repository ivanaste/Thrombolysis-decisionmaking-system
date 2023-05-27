export class TrenutakNastanka {
  constructor(
    public jmbgPacijenta: string,
    public datumRodjenjaPacijenta: string,
    public nastaliUTokuSna: boolean,
    public postojeSvedoci: boolean,
    public trenutakNastanka: Date,
    public stanjeSvesti: string
  ) {}
}
