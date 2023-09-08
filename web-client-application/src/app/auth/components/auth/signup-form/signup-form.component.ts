import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Store} from "@ngrx/store";
import {patientSignup, workerSignup} from '../../../store/auth.actions';
import {Subscription} from "rxjs";
import {selectRole} from "../../../store/auth.selectors";

@Component({
  selector: 'app-signup-form',
  templateUrl: './signup-form.component.html',
  styleUrls: ['./signup-form.component.scss']
})
export class SignupFormComponent implements OnInit {
  hidePassword = true;
  signupForm!: FormGroup;
  storeSubscription!: Subscription;
  userRole: string | null = null;

  constructor(private store: Store) {
  }

  ngOnInit(): void {
    this.signupForm = new FormGroup({
      email: new FormControl('istevanovic3112@gmail.com', [Validators.required, Validators.email]),
      jmbg: new FormControl('3112232731', [Validators.required]),
      ime: new FormControl('Ivana', [Validators.required]),
      prezime: new FormControl('Stevanovic', [Validators.required]),
      datumRodjenja: new FormControl(null, [Validators.required]),
      role: new FormControl('DOCTOR', [Validators.required]),
    });
    this.storeSubscription = this.store
      .select(selectRole)
      .subscribe((role) => (this.userRole = role));
  }


  signup() {
    const email = this.signupForm.controls['email'].value;
    const ime = this.signupForm.controls['ime'].value;
    const prezime = this.signupForm.controls['prezime'].value;
    console.log(this.userRole)
    if (this.userRole === 'NURSE') {
      const jmbg = this.signupForm.controls['jmbg'].value;
      const datumRodjenja = this.signupForm.controls['datumRodjenja'].value;
      this.store.dispatch(patientSignup({email, jmbg, ime, prezime, datumRodjenja}));
    } else {
      const uloga = this.signupForm.controls['role'].value;
      console.log(email, uloga, ime, prezime)
      this.store.dispatch(workerSignup({email, ime, prezime, uloga}));
    }
  }

}
