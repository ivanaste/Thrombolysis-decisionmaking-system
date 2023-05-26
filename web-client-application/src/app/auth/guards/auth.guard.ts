import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { Observable, of, switchMap, take } from 'rxjs';
import { Store } from '@ngrx/store';
import { selectToken } from '../store/auth.selectors';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private store: Store) {}

  canActivate():
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    return this.store.select(selectToken).pipe(
      take(1),
      switchMap((token) => {
        if (token) {
          return of(true);
        }
        return of(this.router.createUrlTree(['/auth/login']));
      })
    );
  }
}
