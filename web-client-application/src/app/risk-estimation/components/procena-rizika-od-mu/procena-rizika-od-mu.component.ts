import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Store} from "@ngrx/store";
import * as RiskActions from '../../store/risk-estimation-actions';

@Component({
  selector: 'app-procena-rizika-od-mu',
  templateUrl: './procena-rizika-od-mu.component.html',
  styleUrls: ['./procena-rizika-od-mu.component.scss']
})
export class ProcenaRizikaOdMUComponent {
  procenaRizikaForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private store: Store) {
    this.procenaRizikaForm = this.formBuilder.group({
      jmbgPacijenta: ['', Validators.required],
      trajanjeSimptoma: ['', Validators.required],
      klinickiSimptom: ['', Validators.required],
      stenozaKrvnogSuda: ['', Validators.required],
      dijabetes: ['', Validators.required],
      datumRodjenjaPacijenta: [new Date(2000, 0, 1), Validators.required],
    });
  }

  proceniRizikOdMU() {
    const formValue = this.procenaRizikaForm.value;
    const klinickiSimptomi = this.dobaviKlinickeSimptome();
    this.store.dispatch(RiskActions.calculateRisk({
      request: {
        jmbgPacijenta: formValue.jmbgPacijenta,
        hemipareza: klinickiSimptomi.hemipareza,
        hemiplegija: klinickiSimptomi.hemiplegija,
        smetnjeGovora: klinickiSimptomi.smetnjeGovora,
        trajanjeSimptoma: formValue.trajanjeSimptoma,
        dijabetes: formValue.dijabetes,
        stenozaSimptomatskogKrvnogSuda: formValue.stenozaKrvnogSuda,
        datumRodjenjaPacijenta: this.adjustTimeZoneOffset(formValue.datumRodjenjaPacijenta),
      }
    }));

  }

  private adjustTimeZoneOffset(date: Date): Date {
    const timezoneOffset = date.getTimezoneOffset();
    date.setMinutes(date.getMinutes() - timezoneOffset);
    return date;
  }

  dobaviKlinickeSimptome() {
    const klinickiSimptomi = {hemipareza: false, hemiplegija: false, smetnjeGovora: false}
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    klinickiSimptomi[this.procenaRizikaForm.get('klinickiSimptom')?.value] = true;
    return klinickiSimptomi;
  }

}
