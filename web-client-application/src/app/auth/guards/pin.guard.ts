import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { Observable, of, switchMap, take } from 'rxjs';
import { Store } from '@ngrx/store';
import { selectUserTryingToLogin } from '../store/auth.selectors';

@Injectable({
  providedIn: 'root',
})
export class PinGuard implements CanActivate {
  constructor(private router: Router, private store: Store) {}

  canActivate():
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    return this.store.select(selectUserTryingToLogin).pipe(
      take(1),
      switchMap((userTryingToLogin) => {
        if (userTryingToLogin) {
          return of(true);
        }
        return of(this.router.createUrlTree(['/auth/login']));
      })
    );
  }
}
