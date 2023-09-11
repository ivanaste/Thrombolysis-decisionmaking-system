export class NIHHS {
  constructor(
    public jmbgPacijenta?: string,
    public idOdluke?: string,
    public stanjeSvesti?: number,
    public stanjeSvestiPitanja?: number,
    private stanjeSvestiNalozi?: number,
    private pokretiBulbusa?: number,
    private sirinaVidnogPolja?: number,
    private mimicnaPareza?: number,
    private motorikaLevaRuka?: number,
    private motorikaDesnaRuka?: number,
    private motorikaLevaNoga?: number,
    private motorikaDesnaNoga?: number,
    private ataksijaEkstremiteta?: number,
    private senzibilitet?: number,
    public govor?: number,
    public dizartrija?: number,
    public fenomenNeglekta?: number
  ) {
  }
}
