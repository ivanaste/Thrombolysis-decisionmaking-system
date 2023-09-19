export class TrenutakNastanka {
  constructor(
    public jmbgPacijenta: string,
    public nastaliUTokuSna: boolean,
    public postojeSvedoci: boolean,
    public trenutakNastanka: Date,
    public stanjeSvesti: string
  ) {
  }
}
