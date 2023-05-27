import {Actions, createEffect, ofType} from "@ngrx/effects";
import * as DecisionActions from "../store/decisions-actions";
import {map, switchMap} from "rxjs";
import {Injectable} from "@angular/core";
import {NotifierService} from "../../core/notifier.service";
import {Router} from "@angular/router";
import {DecisionsHttpService} from "../service/decisions-http.service";
import {StatusOdluke} from "../model/StatusOdluke";


@Injectable()
export class DecisionsEffects {
  getDecisions = createEffect(() => {
    return this.actions$.pipe(
      ofType(DecisionActions.getDecisions.type),
      switchMap(() => {
        return this.httpService
          .getDecisions()
          .pipe(map((decisions) => {
            return DecisionActions.setDecisions({decisions})
          }));
      })
    );
  });

  dobaviOdlukuPrvaFaza = createEffect(() => {
    return this.actions$.pipe(
      ofType(DecisionActions.dobaviOdlukuPrvaFaza.type),
      switchMap((action) => {
        return this.httpService
          .proveriOdlukuPrvaFaza(action.trenutakNastanka)
          .pipe(map((odluka) => {
            let status = odluka ? StatusOdluke.PRIHVACENA_FAZA_1 : StatusOdluke.ODBIJENA;
            return DecisionActions.setStatusOdluke({status: status})
          }));
      })
    );
  });


  constructor(
    private notifierService: NotifierService,
    private router: Router,
    private actions$: Actions<DecisionActions.DecisionsActionsUnion>,
    private httpService: DecisionsHttpService
  ) {
  }
}
