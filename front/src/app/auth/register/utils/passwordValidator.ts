import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function passwordValidator(): ValidatorFn {
  const PASSWORD_PATTERN = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/; // 1 lowercase, 1 uppercase, 1 number, 1 special character, 8 characters
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value || '';
    const isValid = PASSWORD_PATTERN.test(value);
    return isValid ? null : { invalidPassword: true };
  };
}
