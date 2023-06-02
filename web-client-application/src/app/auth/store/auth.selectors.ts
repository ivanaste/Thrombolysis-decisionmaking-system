import { createFeatureSelector, createSelector } from '@ngrx/store';
import { State } from './auth.reducer';
import jwtDecode from 'jwt-decode';
import { DecodedJwt } from '../model/decoded-jwt.model';

export const selectAuth = createFeatureSelector<State>('auth');

export const selectUserTryingToLogin = createSelector(
  selectAuth,
  (state: State) => state.userTryingToLogin
);

export const selectToken = createSelector(
  selectAuth,
  (state: State) => state.token
);

export const selectDecodedToken = createSelector(selectToken, (token) => {
  if (token) {
    return jwtDecode<DecodedJwt>(token);
  }
  return null;
});

export const selectRole = createSelector(selectDecodedToken, (decodedToken) => {
  if (decodedToken) {
    return decodedToken.role;
  }
  return null;
});

export const selectTokenExpirationTime = createSelector(
  selectDecodedToken,
  (decodedToken) => {
    if (decodedToken) {
      return decodedToken.exp;
    }
    return null;
  }
);
