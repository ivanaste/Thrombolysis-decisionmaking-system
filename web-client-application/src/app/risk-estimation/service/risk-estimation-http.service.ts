import {Inject, Injectable} from "@angular/core";
import {APP_SERVICE_CONFIG, AppConfig} from "../../app-config/app-config";
import {HttpClient} from "@angular/common/http";
import {RiskEstimation} from "../model/risk-estimation";
import {ProcenaRizikaOdMURequest} from "../model/procena-rizika-od-mu";
import {NivoRizikaMu} from "../model/nivo-rizika-mu";

@Injectable({
  providedIn: 'root',
})
export class RiskEstimationHttpService {
  GET_RISKS = 'nivoRizika/all';
  UTVRDI_RIZIK = 'nivoRizika/rizikOdMU';

  constructor(
    @Inject(APP_SERVICE_CONFIG) private config: AppConfig,
    private http: HttpClient
  ) {
  }

  getRisks() {
    return this.http.get<RiskEstimation[]>(this.config.apiEndpoint + this.GET_RISKS, {})
  }

  utvrdiRizik(procenaRizika: ProcenaRizikaOdMURequest) {
    return this.http.post<NivoRizikaMu>(this.config.apiEndpoint + this.UTVRDI_RIZIK, procenaRizika)
  }
}
