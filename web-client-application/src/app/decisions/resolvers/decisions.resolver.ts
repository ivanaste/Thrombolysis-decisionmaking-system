import {Injectable} from "@angular/core";
import {Resolve} from "@angular/router";
import {Store} from "@ngrx/store";
import {Actions, ofType} from "@ngrx/effects";
import * as DecisionActions from '../store/decisions-actions'
import {map, Observable, take} from "rxjs";
import {Decision} from "../model/decision";


@Injectable({
  providedIn: 'root',
})
export class DecisionsResolver implements Resolve<Decision[]> {
  constructor(
    private store: Store,
    private actions$: Actions<DecisionActions.DecisionsActionsUnion>
  ) {
  }

  resolve(): Observable<Decision[]> | Promise<Decision[]> | Decision[] {
    this.store.dispatch(DecisionActions.getDecisions());
    return this.actions$.pipe(
      ofType(DecisionActions.setDecisions.type),
      take(1),
      map((action) => action.decisions)
    );
  }
}
