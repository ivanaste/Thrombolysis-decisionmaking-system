import {Inject, Injectable} from "@angular/core";
import {APP_SERVICE_CONFIG, AppConfig} from "../../app-config/app-config";
import {HttpClient} from "@angular/common/http";
import {RiskEstimation} from "../model/risk-estimation";

@Injectable({
  providedIn: 'root',
})
export class RiskEstimationHttpService {
  GET_RISKS = 'nivoRizika/all';

  constructor(
    @Inject(APP_SERVICE_CONFIG) private config: AppConfig,
    private http: HttpClient
  ) {
  }

  getRisks() {
    return this.http.get<RiskEstimation[]>(this.config.apiEndpoint + this.GET_RISKS, {})
  }
}
