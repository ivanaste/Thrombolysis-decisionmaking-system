import { AbstractControl, ValidatorFn } from '@angular/forms';

export const userTypeValidator: ValidatorFn = (
  control: AbstractControl
): { [key: string]: boolean } | null => {
  const input = control.value;
  const userTypes = ['TENANT', 'OWNER', 'ADMIN'];

  if (userTypes.indexOf(input) === -1) {
    return { userType: true };
  }
  return null;
};
