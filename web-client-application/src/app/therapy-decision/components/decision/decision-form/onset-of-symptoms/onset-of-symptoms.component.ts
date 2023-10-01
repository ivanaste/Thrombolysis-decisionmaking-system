import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {TrenutakNastanka} from "../../../../model/TrenutakNastanka";
import {Decision} from "../../../../model/decision";
import {DecisionStatus} from "../../../../model/decision-status";
import {take} from "rxjs";
import {selectRole} from "../../../../../auth/store/auth.selectors";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-trenutak-nastanka-simptoma',
  templateUrl: './onset-of-symptoms.component.html',
  styleUrls: ['./onset-of-symptoms.component.scss']
})
export class OnsetOfSymptomsComponent implements OnInit {
  myForm: FormGroup;
  jmbgPacijenta: string;
  stanjaSvesti: string[] = ['Pri svesti', 'Poremecaj u razumevanju govora', 'Poremecaj u izrazavanju govora', 'Mentalno retardiran', 'Dementan']
  @ViewChild('timepicker') timepicker: any;

  @Output() trenutakNastankaObj: EventEmitter<TrenutakNastanka> = new EventEmitter<TrenutakNastanka>();
  @Input() odlukaOTrombolizi: Decision | null;

  constructor(private formBuilder: FormBuilder, private route: ActivatedRoute) {
    this.myForm = this.formBuilder.group({
      jmbgPacijenta: new FormControl({value: null, disabled: true}),
      simptomiNastaliUTokuSna: [''],
      postojeSvedoci: [''],
      vremeNastankaSimptoma: ['', Validators.required],
      datumNastankaSimptoma: [new Date(), Validators.required],
      stanjeSvesti: ['Pri svesti', Validators.required]
    });
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.jmbgPacijenta = params.get('jmbg') || '';
      this.myForm.get('jmbgPacijenta')?.setValue(this.jmbgPacijenta);
    })
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
    if (this.myForm.valid) {
      const formValues = this.myForm.value;
      const trenutakNastanka = new TrenutakNastanka(
        this.jmbgPacijenta,
        formValues.simptomiNastaliUTokuSna,
        formValues.postojeSvedoci,
        this.mergeTimeWithDate(formValues.vremeNastankaSimptoma, formValues.datumNastankaSimptoma),
        formValues.stanjeSvesti.toUpperCase().replace(/ /g, "_")
      );
      this.trenutakNastankaObj.emit(trenutakNastanka);
    }
  }

  mergeTimeWithDate(time: string, date: Date): Date {
    const [hours, minutes] = time.split(':');
    const mergedDateTime = new Date(date);
    mergedDateTime.setHours(Number(hours));
    mergedDateTime.setMinutes(Number(minutes));
    return this.adjustTimeZoneOffset(mergedDateTime);
  }

  private adjustTimeZoneOffset(date: Date): Date {
    const timezoneOffset = date.getTimezoneOffset();
    date.setMinutes(date.getMinutes() - timezoneOffset);
    return date;
  }

  protected readonly DecisionStatus = DecisionStatus;
}
