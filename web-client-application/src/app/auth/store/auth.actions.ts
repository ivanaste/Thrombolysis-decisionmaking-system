import {createAction, props, union} from '@ngrx/store';

export const login = createAction(
  '[Auth] Login',
  props<{ email: string; password: string; }>()
);

export const patientSignup = createAction(
  '[Auth] Patient Signup',
  props<{ email: string; jmbg: string; ime: string; prezime: string; datumRodjenja: string}>()
);

export const workerSignup = createAction(
  '[Auth] Worker Signup',
  props<{ email: string; ime: string; prezime: string; uloga: string}>()
);

export const loginSuccess = createAction(
  '[Auth] Login Success',
  props<{ token: string; cookie?: string }>()
);

export const loginFail = createAction('[Auth] Login Fail');

export const autoLogin = createAction('[Auth] Auto Login');

export const autoLoginFail = createAction('[Auth] Auto Login  Failed');

export const logout = createAction('[Auth] Logout');
export const signUp = createAction(
  '[Auth] Sign Up Start',
  props<{ email: string; password: string; role: string }>()
);

export const signUpSuccess = createAction('[Auth] Sign Up Success');

export const confirmEmail = createAction(
  '[Auth] Confirm Email',
  props<{ token: string }>()
);

export const confirmEmailSuccess = createAction('[Auth] Confirm Email Success');

const all = union({
  login,
  autoLogin,
  autoLoginFail,
  loginSuccess,
  loginFail,
  logout,
  signUp,
  signUpSuccess,
  confirmEmail,
  confirmEmailSuccess,
  patientSignup,
  workerSignup
});

export type AuthActionsUnion = typeof all;
