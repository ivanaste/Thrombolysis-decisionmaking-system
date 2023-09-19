import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Store} from "@ngrx/store";
import * as RiskActions from '../../store/risk-estimation-actions';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-procena-rizika-od-mu',
  templateUrl: './procena-rizika-od-mu.component.html',
  styleUrls: ['./procena-rizika-od-mu.component.scss']
})
export class ProcenaRizikaOdMUComponent implements OnInit {
  procenaRizikaForm: FormGroup;
  jmbgPacijenta: string;
  datumRodjenjaPacijenta: Date;

  constructor(private formBuilder: FormBuilder, private store: Store, private route: ActivatedRoute) {
    this.procenaRizikaForm = this.formBuilder.group({
      jmbgPacijenta: new FormControl({value: null, disabled: true}),
      trajanjeSimptoma: ['', Validators.required],
      klinickiSimptom: ['', Validators.required],
      stenozaKrvnogSuda: ['', Validators.required],
      dijabetes: ['', Validators.required],
      datumRodjenjaPacijenta: new FormControl({value: null, disabled: true}),
    });
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.jmbgPacijenta = params.get('jmbg') || '';
      const datumRodjenjaString = params.get('datumRodjenja') || new Date();
      this.datumRodjenjaPacijenta = new Date(datumRodjenjaString);
      this.procenaRizikaForm.get('jmbgPacijenta')?.setValue(this.jmbgPacijenta);
      this.procenaRizikaForm.get('datumRodjenjaPacijenta')?.setValue(this.datumRodjenjaPacijenta);
    })
  }

  proceniRizikOdMU() {
    const formValue = this.procenaRizikaForm.value;
    const klinickiSimptomi = this.dobaviKlinickeSimptome();
    this.store.dispatch(RiskActions.calculateRisk({
      request: {
        jmbgPacijenta: this.jmbgPacijenta,
        hemipareza: klinickiSimptomi.hemipareza,
        hemiplegija: klinickiSimptomi.hemiplegija,
        smetnjeGovora: klinickiSimptomi.smetnjeGovora,
        trajanjeSimptoma: formValue.trajanjeSimptoma,
        dijabetes: formValue.dijabetes,
        stenozaSimptomatskogKrvnogSuda: formValue.stenozaKrvnogSuda,
        datumRodjenjaPacijenta: this.adjustTimeZoneOffset(this.datumRodjenjaPacijenta),
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
