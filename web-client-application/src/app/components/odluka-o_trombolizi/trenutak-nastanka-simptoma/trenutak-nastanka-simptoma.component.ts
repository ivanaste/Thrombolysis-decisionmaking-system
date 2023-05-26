import {Component, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-trenutak-nastanka-simptoma',
  templateUrl: './trenutak-nastanka-simptoma.component.html',
  styleUrls: ['./trenutak-nastanka-simptoma.component.scss']
})
export class TrenutakNastankaSimptomaComponent {
  myForm: FormGroup;
  stanjaSvesti: string[] = ['Pri svesti', 'Poremećaj u razumevanju govora', 'Poremećaj u izražavanju govora', 'Mentalno retardiran', 'Dementan']
  @ViewChild('timepicker') timepicker: any;

  constructor(private formBuilder: FormBuilder) {
    this.myForm = this.formBuilder.group({
      simptomiNastaliUTokuSna: [''],
      postojeSvedoci: [''],
      trenutakNastankaSimptoma: ['', Validators.required],
      stanjeSvesti: ['Pri svesti', Validators.required]
    });
  }

  openFromIcon(timepicker: { open: () => void }) {
    if (!this.myForm.get('trenutakNastankaSimptoma')?.disabled) {
      timepicker.open();
    }
  }

  onClear() {
    this.myForm.get('trenutakNastankaSimptoma')?.setValue(null);
  }

  finishFunction() {
    console.log("op")
    console.log(this.myForm.controls)
  }

}
