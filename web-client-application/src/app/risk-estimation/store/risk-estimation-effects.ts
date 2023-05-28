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

  calculateRisk = createEffect(() => {
    return this.actions$.pipe(
      ofType(RiskEstimationActions.calculateRisk.type),
      switchMap((action) => {
        return this.httpService
          .utvrdiRizik(action.request)
          .pipe(map((rizik) => {
            this.notifierService.notifySuccess("Utvrdjen je nivo rizika: " + rizik.toString());
            return RiskEstimationActions.setRisk({rizik: rizik})
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
