import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root',
})
export class NotifierService {
  constructor(private toast: ToastrService) {}

  public notifyError(errorRes: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred';
    if (errorRes.error) {
      errorMessage = errorRes.error.message;
    }
    this.toast.error(errorMessage, '');
  }

  public notifyErrorMessage(message: string) {
    this.toast.error(message, '');
  }

  public notifySuccess(message: string) {
    this.toast.success(message, '');
  }
}
