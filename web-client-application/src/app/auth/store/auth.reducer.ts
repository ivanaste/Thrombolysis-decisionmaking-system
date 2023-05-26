import {Action, createReducer, on} from '@ngrx/store';
import * as AuthActions from './auth.actions';

export interface State {
  userTryingToLogin: { email: string; password: string } | null;
  token: string | null;
}

const initialState: State = {
  userTryingToLogin: null,
  token: null,
};

const authReducer = createReducer(
  initialState,
  on(AuthActions.loginSuccess, (state, {token}) => ({
    ...state,
    token,
    userTryingToLogin: null,
  })),
  on(AuthActions.autoLoginFail, (state) => ({
    ...state,
    token: null,
  })),
  on(AuthActions.logout, (state) => ({
    ...state,
    token: null,
  }))
);

export function reducer(state: State | undefined, action: Action) {
  return authReducer(state, action);
}
