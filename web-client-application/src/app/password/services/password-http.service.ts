import { Inject, Injectable } from '@angular/core';
import { APP_SERVICE_CONFIG, AppConfig } from '../../app-config/app-config';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class PasswordHttpService {
  REQUEST_PASSWORD_CHANGE = 'password/request-change';
  CHANGE_PASSWORD = 'password/change';

  constructor(
    @Inject(APP_SERVICE_CONFIG) private config: AppConfig,
    private http: HttpClient
  ) {}

  requestPasswordChange(email: string) {
    return this.http.get(
      this.config.apiEndpoint + this.REQUEST_PASSWORD_CHANGE,
      {
        params: new HttpParams().append('email', email),
      }
    );
  }

  changePassword(
    newPassword: string,
    newPasswordConfirmation: string,
    authToken: string
  ) {
    return this.http.put(this.config.apiEndpoint + this.CHANGE_PASSWORD, {
      newPassword,
      newPasswordConfirmation,
      authToken,
    });
  }
}
