import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Store} from '@ngrx/store';
import {login} from '../../../store/auth.actions';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss'],
})
export class LoginFormComponent implements OnInit {
  hidePassword = true;
  loginForm!: FormGroup;

  constructor(private store: Store) {
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      email: new FormControl(null, [Validators.required, Validators.email]),
      password: new FormControl(null, [
        Validators.required,
        // passwordValidator
      ]),
    });
  }

  toggleShowPassword(event: Event) {
    event.preventDefault();
    this.hidePassword = !this.hidePassword;
  }

  login() {
    if (this.loginForm.valid) {
      const email = this.loginForm.controls['email'].value;
      const password = this.loginForm.controls['password'].value;
      this.store.dispatch(login({email, password}));
    }
  }
}
