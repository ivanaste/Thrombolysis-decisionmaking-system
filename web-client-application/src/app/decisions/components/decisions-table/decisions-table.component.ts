import {Component, OnInit} from '@angular/core';
import {Store} from "@ngrx/store";
import {Observable} from "rxjs";
import {Decision} from "../../model/decision";
import {selectDecisions} from "../../store/decisions-selectors";

@Component({
  selector: 'app-decisions-table',
  templateUrl: './decisions-table.component.html',
  styleUrls: ['./decisions-table.component.scss']
})
export class DecisionsTableComponent implements OnInit {
  decisions$: Observable<Decision[]>;

  constructor(private store: Store) {
  }

  ngOnInit(): void {
    this.decisions$ = this.store.select(selectDecisions);
  }
}
