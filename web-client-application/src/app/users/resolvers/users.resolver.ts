import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { map, Observable, take } from 'rxjs';
import { User } from '../../shared/model/user.model';
import { Store } from '@ngrx/store';
import { Actions, ofType } from '@ngrx/effects';
import * as UsersActions from '../store/users.actions';

@Injectable({
  providedIn: 'root',
})
export class UsersResolver implements Resolve<User[]> {
  constructor(
    private store: Store,
    private actions$: Actions<UsersActions.UsersActionsUnion>
  ) {}

  resolve(): Observable<User[]> | Promise<User[]> | User[] {
    this.store.dispatch(UsersActions.getUsers({}));
    return this.actions$.pipe(
      ofType(UsersActions.setUsers.type),
      take(1),
      map((action) => action.userPage.items)
    );
  }
}
