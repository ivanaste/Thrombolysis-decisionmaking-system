import { createAction, props, union } from '@ngrx/store';

export const requestPasswordChange = createAction(
  '[Password] Request Password Change',
  props<{ email: string }>()
);

export const requestPasswordChangeSuccess = createAction(
  '[Password] Request Password Change Success'
);

export const changePassword = createAction(
  '[Password] Change Password',
  props<{
    newPassword: string;
    newPasswordConfirmation: string;
    authToken: string;
  }>()
);

export const changePasswordSuccess = createAction(
  '[Password] Change Password Success'
);

const all = union({
  requestPasswordChange,
  requestPasswordChangeSuccess,
  changePassword,
  changePasswordSuccess,
});

export type PasswordActionsUnion = typeof all;
