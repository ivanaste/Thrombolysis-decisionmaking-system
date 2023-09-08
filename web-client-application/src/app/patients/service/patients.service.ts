import {Inject, Injectable} from '@angular/core';
import {APP_SERVICE_CONFIG, AppConfig} from "../../app-config/app-config";
import {HttpClient} from "@angular/common/http";
import {Patient} from "../model/Patient";

@Injectable({
  providedIn: 'root'
})
export class PatientsService {
  GET_PATIENTS = 'pacijent'

  constructor(@Inject(APP_SERVICE_CONFIG) private config: AppConfig,
              private http: HttpClient) {

  }

  getAll() {
    return this.http.get<Patient[]>(this.config.apiEndpoint + this.GET_PATIENTS, {})
  }
}
