import {Inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthToken} from '../model/auth-token.model';
import {APP_SERVICE_CONFIG, AppConfig} from '../../app-config/app-config';

@Injectable({
  providedIn: 'root',
})
export class AuthHttpService {
  LOGIN_FIRST_STEP = 'auth/login-details-exist';
  LOGIN_SECOND_STEP = 'auth/login';
  LOGOUT = 'auth/logout';
  SIGN_UP = 'auth/register';
  EMAIL_CONFIRMATION = 'auth/activateEmail/';

  constructor(
    @Inject(APP_SERVICE_CONFIG) private config: AppConfig,
    private http: HttpClient
  ) {
  }

  sendFirstStepLoginRequest(email: string, password: string) {
    return this.http.post<number>(
      this.config.apiEndpoint + this.LOGIN_FIRST_STEP,
      {
        email,
        password,
      }
    );
  }

  sendSecondStepLoginRequest(email: string, password: string) {
    return this.http.post<AuthToken>(
      this.config.apiEndpoint + this.LOGIN_SECOND_STEP,
      {
        email,
        password,
      },
      {
        withCredentials: true,
      }
    );
  }

  sendLogoutRequest() {
    return this.http.post(this.config.apiEndpoint + this.LOGOUT, {});
  }

  sendSignUpRequest(email: string, role: string, ime: string, prezime: string, jmbg?: string, datumRodjenja?: string) {
    let body;
    if (role === 'PATIENT') {
      body = {
        email,
        role,
        jmbg,
        ime,
        prezime,
        datumRodjenja
      }
    } else {
      body = {
        email,
        role,
        ime,
        prezime,
      }
    }
    return this.http.post(this.config.apiEndpoint + this.SIGN_UP, body);
  }

  sendEmailConfirmationRequest(token: string) {
    return this.http.put(
      this.config.apiEndpoint + this.EMAIL_CONFIRMATION + token,
      {}
    );
  }
}
