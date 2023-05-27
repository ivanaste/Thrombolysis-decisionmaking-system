import {Actions, createEffect, ofType} from "@ngrx/effects";
import {map, switchMap} from "rxjs";
import {Injectable} from "@angular/core";
import {NotifierService} from "../../core/notifier.service";
import {Router} from "@angular/router";
import {RiskEstimationHttpService} from "../service/risk-estimation-http.service";
import * as RiskEstimationActions from "../store/risk-estimation-actions";


@Injectable()
export class RiskEstimationEffects {
  getRiskEstimations = createEffect(() => {
    return this.actions$.pipe(
      ofType(RiskEstimationActions.getRiskEstimations.type),
      switchMap(() => {
        return this.httpService
          .getRisks()
          .pipe(map((riskEstimations) => {
            return RiskEstimationActions.setRiskEstimations({riskEstimations: riskEstimations})
          }));
      })
    );
  });


  constructor(
    private notifierService: NotifierService,
    private router: Router,
    private actions$: Actions<RiskEstimationActions.RiskEstimationActionsUnion>,
    private httpService: RiskEstimationHttpService
  ) {
  }
}
