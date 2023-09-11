import {Component, OnInit} from '@angular/core';
import {TrenutakNastanka} from "../../../model/TrenutakNastanka";
import {Store} from "@ngrx/store";
import * as DecisionActions from '../../../store/decisions-actions'
import {Observable} from "rxjs";
import {Decision} from "../../../model/decision";
import {selectOdluka} from "../../../store/decisions-selectors";
import {DecisionStatus} from "../../../model/decision-status";
import {NeuroloskiPregled} from "../../../model/NeuroloskiPregled";
import {NIHHS} from "../../../model/NIHHS";

@Component({
  selector: 'app-odluka-otrombolizi',
  templateUrl: './decision-form.component.html',
  styleUrls: ['./decision-form.component.scss']
})
export class DecisionFormComponent implements OnInit {
  odlukaOTrombolizi$: Observable<Decision>;

  constructor(private store: Store) {
  }

  ngOnInit(): void {
    this.odlukaOTrombolizi$ = this.store.select(selectOdluka);
    this.odlukaOTrombolizi$.subscribe(decision => {
      console.log(decision);
    });
  }

  proveriOdlukuPrvaFaza(data: TrenutakNastanka) {
    this.store.dispatch(DecisionActions.dobaviOdlukuPrvaFaza({trenutakNastanka: data}));
  }

  proveriOdlukuDrugaFaza(data: NeuroloskiPregled) {
    console.log(data)
    //data.idOdluke = "61ec0828-2931-4898-99da-82c935c96f6b"
    this.store.dispatch(DecisionActions.dobaviOdlukuDrugaFaza({neuroloskiPregled: data}));
  }

  proveriOdlukuTrecaFaza(data: NIHHS) {
    console.log(data)
    //data.idOdluke = "61ec0828-2931-4898-99da-82c935c96f6b"
    this.store.dispatch(DecisionActions.dobaviOdlukuTrecaFaza({nihhs: data}));
  }

  protected readonly DecisionStatus = DecisionStatus;
}
