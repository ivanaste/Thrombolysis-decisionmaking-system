import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { logout } from '../store/auth.actions';
import { selectTokenExpirationTime } from '../store/auth.selectors';
import { take, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private tokenExpirationTimer: NodeJS.Timeout | null = null;

  constructor(private store: Store) {}

  setLogoutTimer() {
    this.store.select(selectTokenExpirationTime).pipe(
      take(1),
      tap((expirationTime) => {
        if (expirationTime) {
          this.tokenExpirationTimer = setTimeout(() => {
            this.store.dispatch(logout());
          }, expirationTime);
        }
      })
    );
  }

  clearLogoutTimer() {
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
      this.tokenExpirationTimer = null;
    }
  }
}
