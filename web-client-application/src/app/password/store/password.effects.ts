import { Injectable } from '@angular/core';
import { NotifierService } from '../../core/notifier.service';
import { Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as PasswordActions from '../store/password.actions';
import { PasswordHttpService } from '../services/password-http.service';
import { map, switchMap } from 'rxjs';

@Injectable()
export class PasswordEffects {
  requestPasswordChange = createEffect(() => {
    return this.actions$.pipe(
      ofType(PasswordActions.requestPasswordChange.type),
      switchMap((action) => {
        return this.httpService
          .requestPasswordChange(action.email)
          .pipe(map(() => PasswordActions.requestPasswordChangeSuccess()));
      })
    );
  });

  changePassword = createEffect(() => {
    return this.actions$.pipe(
      ofType(PasswordActions.changePassword.type),
      switchMap((action) => {
        return this.httpService
          .changePassword(
            action.newPassword,
            action.newPasswordConfirmation,
            action.authToken
          )
          .pipe(map(() => PasswordActions.changePasswordSuccess()));
      })
    );
  });

  requestPasswordChangeSuccess = createEffect(
    () => {
      return this.actions$.pipe(
        ofType(PasswordActions.requestPasswordChangeSuccess.type),
        map(() => {
          const message =
            'Email has been sent to the address. Check your email.';
          this.notifierService.notifySuccess(message);
        })
      );
    },
    { dispatch: false }
  );

  changePasswordSuccess = createEffect(
    () => {
      return this.actions$.pipe(
        ofType(PasswordActions.changePasswordSuccess.type),
        map(() => {
          const message = 'You have successfully changed your password.';
          this.notifierService.notifySuccess(message);
          this.router.navigate(['/']);
        })
      );
    },
    { dispatch: false }
  );

  constructor(
    private notifierService: NotifierService,
    private router: Router,
    private actions$: Actions<PasswordActions.PasswordActionsUnion>,
    private httpService: PasswordHttpService
  ) {}
}
