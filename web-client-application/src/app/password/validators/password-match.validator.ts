import { AbstractControl, ValidatorFn } from '@angular/forms';

export const passwordMatchValidator: ValidatorFn = (
  control: AbstractControl
): { [key: string]: boolean } | null => {
  const password = control.root.get('newPassword')?.value;
  const value = control.value;

  if (value !== password) {
    return { passwordMismatch: true };
  } else {
    return null;
  }
};
