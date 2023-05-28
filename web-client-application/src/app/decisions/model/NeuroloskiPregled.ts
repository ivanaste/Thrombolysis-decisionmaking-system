export class NeuroloskiPregled {
  constructor(
    public kontraindikacije: string[],
    public datumiDesavanjaKontraindikacija: (Date | null)[],
    public idOdluke?: string
  ) {
  }
}
