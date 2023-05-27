import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-procena-rizika-od-mu',
  templateUrl: './procena-rizika-od-mu.component.html',
  styleUrls: ['./procena-rizika-od-mu.component.scss']
})
export class ProcenaRizikaOdMUComponent {
  procenaRizikaForm: FormGroup;

  constructor(private formBuilder: FormBuilder) {
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
    console.log(this.procenaRizikaForm.value)


  }

  dobaviKlinickeSimptome() {
    let klinickiSimptomi = {hemipareza: false, hemiplegija: false, smetnjeGovora: false}
    // @ts-ignore
    klinickiSimptomi[this.procenaRizikaForm.get('klinickiSimptom')?.value] = true;
    return klinickiSimptomi;
  }

}
