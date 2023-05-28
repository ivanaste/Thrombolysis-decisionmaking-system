export interface ProcenaRizikaOdMURequest {
  jmbgPacijenta: string;
  hemipareza: boolean;
  hemiplegija: boolean;
  smetnjeGovora: boolean;
  trajanjeSimptoma: number | null;
  dijabetes: boolean;
  stenozaSimptomatskogKrvnogSuda: number | null;
  datumRodjenjaPacijenta: Date;
}
