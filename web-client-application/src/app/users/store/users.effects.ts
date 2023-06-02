import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { NotifierService } from '../../core/notifier.service';
import { Router } from '@angular/router';
import { UsersHttpService } from '../services/users-http.service';
import * as UsersActions from './users.actions';
import { map, switchMap } from 'rxjs';
import { SortDirection } from '../../shared/model/sort-direction';

@Injectable()
export class UsersEffects {
  getUsers = createEffect(() => {
    return this.actions$.pipe(
      ofType(UsersActions.getUsers.type),
      switchMap((action) => {
        return this.httpService
          .getUsers(
            action.pageSize,
            action.pageNumber,
            action.searchContent,
            action.sortField,
            action.sortDirection,
            action.userRole
          )
          .pipe(map((userPage) => UsersActions.setUsers({ userPage })));
      })
    );
  });

  createUser = createEffect(() => {
    return this.actions$.pipe(
      ofType(UsersActions.createUser.type),
      switchMap((action) => {
        return this.httpService
          .createUser(action.email, action.role)
          .pipe(map(() => UsersActions.createUserSuccess()));
      })
    );
  });

  deleteUser = createEffect(() => {
    return this.actions$.pipe(
      ofType(UsersActions.deleteUser.type),
      switchMap((action) => {
        return this.httpService
          .deleteUser(action.id)
          .pipe(map(() => UsersActions.deleteUserSuccess()));
      })
    );
  });

  changeUserRole = createEffect(() => {
    return this.actions$.pipe(
      ofType(UsersActions.changeUserRole.type),
      switchMap((action) => {
        return this.httpService
          .changeUserRole(action.id, action.role)
          .pipe(
            map(() =>
              UsersActions.userChangeSuccess({
                id: action.id,
                role: action.role,
              })
            )
          );
      })
    );
  });

  userChangedSuccess = createEffect(
    () => {
      return this.actions$.pipe(
        ofType(UsersActions.userChangeSuccess.type),
        map((action) => {
          const message = 'You have successfully updated user.';
          this.notifierService.notifySuccess(message);
          this.router.navigate(['/admin/users/user/' + action.id]);
        })
      );
    },
    { dispatch: false }
  );

  deleteUserSuccess = createEffect(
    () => {
      return this.actions$.pipe(
        ofType(UsersActions.deleteUserSuccess.type),
        map(() => {
          const message = 'You have successfully removed user.';
          this.notifierService.notifySuccess(message);
          this.router.navigate(['/admin/users/all']);
        })
      );
    },
    { dispatch: false }
  );

  createUserSuccess = createEffect(
    () => {
      return this.actions$.pipe(
        ofType(UsersActions.createUserSuccess.type),
        map(() => {
          const message = 'You have successfully created a user.';
          this.notifierService.notifySuccess(message);
          this.router.navigate(['/admin/users/all']);
        })
      );
    },
    { dispatch: false }
  );

  searchUserEmails = createEffect(() => {
    return this.actions$.pipe(
      ofType(UsersActions.searchUserEmails.type),
      switchMap((action) => {
        return this.httpService
          .getUsers(10, 0, action.value, 'email', SortDirection.ASC)
          .pipe(
            map((userPage) => {
              return UsersActions.setUserEmailsSearchResult({
                users: userPage.items,
              });
            })
          );
      })
    );
  });

  constructor(
    private notifierService: NotifierService,
    private router: Router,
    private actions$: Actions<UsersActions.UsersActionsUnion>,
    private httpService: UsersHttpService
  ) {}
}
