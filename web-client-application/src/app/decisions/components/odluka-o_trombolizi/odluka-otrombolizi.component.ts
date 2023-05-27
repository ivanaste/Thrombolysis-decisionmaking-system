import {Component, OnInit} from '@angular/core';
import {TrenutakNastanka} from "../../model/TrenutakNastanka";
import {Store} from "@ngrx/store";
import * as DecisionActions from '../../store/decisions-actions'
import {Observable} from "rxjs";
import {Decision} from "../../model/decision";
import {selectOdluka} from "../../store/decisions-selectors";

@Component({
  selector: 'app-odluka-otrombolizi',
  templateUrl: './odluka-otrombolizi.component.html',
  styleUrls: ['./odluka-otrombolizi.component.scss']
})
export class OdlukaOTromboliziComponent implements OnInit {
  odlukaOTrombolizi$: Observable<Decision>;

  constructor(private store: Store) {
  }

  ngOnInit(): void {
    this.odlukaOTrombolizi$ = this.store.select(selectOdluka);
  }

  proveriOdlukuPrvaFaza(data: TrenutakNastanka) {
    console.log(data);
    this.store.dispatch(DecisionActions.dobaviOdlukuPrvaFaza({trenutakNastanka: data}));
  }

}
