import {Inject, Injectable} from "@angular/core";
import {APP_SERVICE_CONFIG, AppConfig} from "../../app-config/app-config";
import {HttpClient} from "@angular/common/http";
import {Decision} from "../model/decision";
import {TrenutakNastanka} from "../model/TrenutakNastanka";

@Injectable({
  providedIn: 'root',
})
export class DecisionsHttpService {
  GET_DECISIONS = 'odluka/all';
  GET_ODLUKA_PRVA_FAZA = 'odluka/nastanakSimptoma'

  constructor(
    @Inject(APP_SERVICE_CONFIG) private config: AppConfig,
    private http: HttpClient
  ) {
  }

  getDecisions() {
    return this.http.get<Decision[]>(this.config.apiEndpoint + this.GET_DECISIONS, {})
  }

  proveriOdlukuPrvaFaza(trenutakNastanka: TrenutakNastanka) {
    return this.http.post<boolean>(this.config.apiEndpoint + this.GET_ODLUKA_PRVA_FAZA, trenutakNastanka);
  }
}
