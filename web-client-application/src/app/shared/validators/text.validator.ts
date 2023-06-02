import { AbstractControl, ValidatorFn } from '@angular/forms';

export const textValidator: ValidatorFn = (
  control: AbstractControl
): { [key: string]: boolean } | null => {
  const input = control.value;
  const textRegex = /^[A-Za-z\s]*$/;

  if (textRegex.test(input)) {
    return { text: true };
  }
  return null;
};
