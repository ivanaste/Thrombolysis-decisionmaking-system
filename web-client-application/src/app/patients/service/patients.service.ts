import {Inject, Injectable} from '@angular/core';
import {APP_SERVICE_CONFIG, AppConfig} from "../../app-config/app-config";
import {HttpClient} from "@angular/common/http";
import {User} from "../model/User";
import {Patient} from "../../shared/model/patient";

@Injectable({
  providedIn: 'root'
})
export class PatientsService {
  GET_PATIENTS = 'pacijent'
  GET_USER = 'korisnik/'
  CHANGE_USER = 'korisnik'

  constructor(@Inject(APP_SERVICE_CONFIG) private config: AppConfig,
              private http: HttpClient) {

  }

  getAll() {
    return this.http.get<User[]>(this.config.apiEndpoint + this.GET_PATIENTS, {})
  }

  getUser(email: string | null) {
    return this.http.get<User>(this.config.apiEndpoint + this.GET_USER + email, {})
  }

  setUser(user: User) {
    return this.http.put<User>(this.config.apiEndpoint + this.CHANGE_USER, {
      email: user.email,
      ime: user.name,
      prezime: user.surname,
      datumRodjenja: user.datumRodjenja
    })
  }
}
