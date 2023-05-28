import {Inject, Injectable} from "@angular/core";
import {APP_SERVICE_CONFIG, AppConfig} from "../../app-config/app-config";
import {HttpClient} from "@angular/common/http";
import {Decision} from "../model/decision";
import {TrenutakNastanka} from "../model/TrenutakNastanka";
import {NeuroloskiPregled} from "../model/NeuroloskiPregled";
import {NIHHS} from "../model/NIHHS";

@Injectable({
  providedIn: 'root',
})
export class DecisionsHttpService {
  GET_DECISIONS = 'odluka/all';
  GET_ODLUKA_PRVA_FAZA = 'odluka/nastanakSimptoma';
  GET_ODLUKA_DRUGA_FAZA = 'odluka/neuroloskiPregled';
  GET_ODLUKA_TRECA_FAZA = 'odluka/nihhsSkor';

  constructor(
    @Inject(APP_SERVICE_CONFIG) private config: AppConfig,
    private http: HttpClient
  ) {
  }

  getDecisions() {
    return this.http.get<Decision[]>(this.config.apiEndpoint + this.GET_DECISIONS, {})
  }

  proveriOdlukuPrvaFaza(trenutakNastanka: TrenutakNastanka) {
    return this.http.post<Decision>(this.config.apiEndpoint + this.GET_ODLUKA_PRVA_FAZA, trenutakNastanka);
  }

  proveriOdlukuDrugaFaza(neuroloskiPregled: NeuroloskiPregled) {
    return this.http.post<Decision>(this.config.apiEndpoint + this.GET_ODLUKA_DRUGA_FAZA, neuroloskiPregled);
  }

  proveriOdlukuTrecaFaza(nihhs: NIHHS) {
    return this.http.post<Decision>(this.config.apiEndpoint + this.GET_ODLUKA_TRECA_FAZA, nihhs);
  }
}
