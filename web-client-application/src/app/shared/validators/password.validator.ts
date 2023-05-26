import { AbstractControl, ValidatorFn } from '@angular/forms';

export const passwordValidator: ValidatorFn = (
  control: AbstractControl
): { [key: string]: boolean } | null => {
  const input = control.value;
  const textRegex =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&#])[A-Za-z\d@$!%*?&#]{12,32}$/;

  if (textRegex.test(input)) {
    return { password: true };
  }
  return null;
};
