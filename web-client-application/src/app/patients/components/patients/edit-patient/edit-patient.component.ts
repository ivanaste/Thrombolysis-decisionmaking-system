import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Observable, Subscription, take} from "rxjs";
import {User} from "../../../model/User";
import {PatientsService} from "../../../service/patients.service";
import {selectRole} from "../../../../auth/store/auth.selectors";
import {Store} from "@ngrx/store";
import {NotifierService} from "../../../../core/notifier.service";

@Component({
  selector: 'app-edit-patient',
  templateUrl: './edit-patient.component.html',
  styleUrls: ['./edit-patient.component.scss']
})
export class EditPatientComponent implements OnInit {
  patientForm!: FormGroup;
  user$: Observable<User>;
  user: User;
  storeSubscription!: Subscription;
  loggedInUserRole: string | null = null;

  constructor(private route: ActivatedRoute, private patientsService: PatientsService, private store: Store, private notifierService: NotifierService) {
  }

  ngOnInit() {
    this.patientForm = new FormGroup({
      email: new FormControl({value: null, disabled: true}, [Validators.required, Validators.email]),
      jmbg: new FormControl({value: null, disabled: true}, [Validators.required]),
      ime: new FormControl(null, [Validators.required]),
      prezime: new FormControl(null, [Validators.required]),
      datumRodjenja: new FormControl(null, [Validators.required]),
    });
    this.route.paramMap.subscribe(params => {
      const userEmail = params.get('email');
      this.user$ = this.patientsService.getUser(userEmail);
      this.user$.pipe(take(1)).subscribe(user => {
        this.user = user;
        this.patientForm.patchValue({
          email: this.user.email,
          jmbg: this.user.jmbg,
          ime: this.user.name,
          prezime: this.user.surname,
          datumRodjenja: new Date(this.user.datumRodjenja)
        });
      })
      this.storeSubscription = this.store
        .select(selectRole)
        .subscribe((role) => (this.loggedInUserRole = role));
    });

  }

  editPatient() {
    this.user.name = this.patientForm.controls['ime'].value;
    this.user.surname = this.patientForm.controls['prezime'].value;
    if (this.loggedInUserRole === 'NURSE') {
      this.user.datumRodjenja = this.patientForm.controls['datumRodjenja'].value
      this.user.datumRodjenja = this.adjustTimeZoneOffset(this.user.datumRodjenja);
    }
    this.patientsService.setUser(this.user).pipe(take(1)).subscribe(() => {
      const message =
        'Uspešno ste izmenili informacije o pacijentu';
      this.notifierService.notifySuccess(message);
      this.ngOnInit();
    })
  }

  private adjustTimeZoneOffset(date: Date): Date {
    const timezoneOffset = date.getTimezoneOffset();
    date.setMinutes(date.getMinutes() - timezoneOffset);
    return date;
  }

}
