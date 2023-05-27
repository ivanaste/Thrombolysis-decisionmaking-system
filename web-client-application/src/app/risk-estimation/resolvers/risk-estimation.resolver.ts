import {Injectable} from "@angular/core";
import {Resolve} from "@angular/router";
import {Store} from "@ngrx/store";
import {Actions, ofType} from "@ngrx/effects";
import * as RiskEstimationActions from '../store/risk-estimation-actions'
import {map, Observable, take} from "rxjs";
import {RiskEstimation} from "../model/risk-estimation";


@Injectable({
  providedIn: 'root',
})
export class RiskEstimationResolver implements Resolve<RiskEstimation[]> {
  constructor(
    private store: Store,
    private actions$: Actions<RiskEstimationActions.RiskEstimationActionsUnion>
  ) {
  }

  resolve(): Observable<RiskEstimation[]> | Promise<RiskEstimation[]> | RiskEstimation[] {
    this.store.dispatch(RiskEstimationActions.getRiskEstimations());
    return this.actions$.pipe(
      ofType(RiskEstimationActions.setRiskEstimations.type),
      take(1),
      map((action) => action.riskEstimations)
    );
  }
}
