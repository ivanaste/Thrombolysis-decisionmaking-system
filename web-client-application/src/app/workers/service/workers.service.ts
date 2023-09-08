import {Inject, Injectable} from '@angular/core';
import {APP_SERVICE_CONFIG, AppConfig} from "../../app-config/app-config";
import {HttpClient} from "@angular/common/http";
import {User} from "../../patients/model/User";

@Injectable({
  providedIn: 'root'
})
export class WorkersService {
  WORKERS = "korisnik"

  constructor(@Inject(APP_SERVICE_CONFIG) private config: AppConfig,
              private http: HttpClient) {

  }

  getAll() {
    return this.http.get<User[]>(this.config.apiEndpoint + this.WORKERS, {})
  }

  delete(id: string) {
    return this.http.delete<User[]>(this.config.apiEndpoint + this.WORKERS + '/' + id);
  }
}
