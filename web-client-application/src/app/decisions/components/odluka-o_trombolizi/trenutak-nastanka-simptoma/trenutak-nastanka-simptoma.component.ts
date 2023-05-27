import {Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TrenutakNastanka} from "../../../model/TrenutakNastanka";
import {Observable} from "rxjs";
import {StatusOdluke} from "../../../model/StatusOdluke";
import {Decision} from "../../../model/decision";

@Component({
  selector: 'app-trenutak-nastanka-simptoma',
  templateUrl: './trenutak-nastanka-simptoma.component.html',
  styleUrls: ['./trenutak-nastanka-simptoma.component.scss']
})
export class TrenutakNastankaSimptomaComponent {
  myForm: FormGroup;
  stanjaSvesti: string[] = ['Pri svesti', 'Poremecaj u razumevanju govora', 'Poremecaj u izrazavanju govora', 'Mentalno retardiran', 'Dementan']
  @ViewChild('timepicker') timepicker: any;

  @Output() trenutakNastankaObj: EventEmitter<TrenutakNastanka> = new EventEmitter<TrenutakNastanka>();
  @Input() odlukaOTrombolizi: Decision | null;

  constructor(private formBuilder: FormBuilder) {
    this.myForm = this.formBuilder.group({
      jmbgPacijenta: ['', Validators.required],
      datumRodjenjaPacijenta: [new Date(2000, 0, 1), Validators.required],
      simptomiNastaliUTokuSna: [''],
      postojeSvedoci: [''],
      vremeNastankaSimptoma: ['', Validators.required],
      datumNastankaSimptoma: [new Date(), Validators.required],
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

  proveriOdluku() {
    const formValues = this.myForm.value;
    const trenutakNastanka = new TrenutakNastanka(
      formValues.jmbgPacijenta,
      formValues.datumRodjenjaPacijenta,
      formValues.simptomiNastaliUTokuSna,
      formValues.postojeSvedoci,
      this.mergeTimeWithDate(formValues.vremeNastankaSimptoma, formValues.datumNastankaSimptoma),
      formValues.stanjeSvesti.toUpperCase().replace(/ /g, "_")
    );
    this.trenutakNastankaObj.emit(trenutakNastanka);
  }

  mergeTimeWithDate(time: string, date: Date): Date {
    const [hours, minutes] = time.split(':');
    let mergedDateTime = new Date(date);
    mergedDateTime.setHours(Number(hours));
    mergedDateTime.setMinutes(Number(minutes));
    return mergedDateTime;
  }

  protected readonly StatusOdluke = StatusOdluke;
}
