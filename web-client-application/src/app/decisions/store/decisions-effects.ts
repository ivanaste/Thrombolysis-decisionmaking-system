import {Actions, createEffect, ofType} from "@ngrx/effects";
import * as DecisionActions from "../store/decisions-actions";
import {map, switchMap} from "rxjs";
import {Injectable} from "@angular/core";
import {NotifierService} from "../../core/notifier.service";
import {Router} from "@angular/router";
import {DecisionsHttpService} from "../service/decisions-http.service";
import {DecisionStatus} from "../model/decision-status";


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
            if (odluka.status === DecisionStatus.ODBIJENA) this.notifierService.notifyErrorMessage("Primena trombolize odbijena");
            else this.notifierService.notifySuccess("Pacijent zadovoljava uslove, predjite na sledeci korak");
            return DecisionActions.setOdluka({odluka: odluka})
          }));
      })
    );
  });

  dobaviOdlukuDrugaFaza = createEffect(() => {
    return this.actions$.pipe(
      ofType(DecisionActions.dobaviOdlukuDrugaFaza.type),
      switchMap((action) => {
        return this.httpService
          .proveriOdlukuDrugaFaza(action.neuroloskiPregled)
          .pipe(map((odluka) => {
            if (odluka.status === DecisionStatus.ODBIJENA) this.notifierService.notifyErrorMessage("Primena trombolize odbijena");
            else this.notifierService.notifySuccess("Pacijent zadovoljava uslove, predjite na sledeci korak");
            return DecisionActions.setOdluka({odluka: odluka})
          }));
      })
    );
  });

  dobaviOdlukuTrecaFaza = createEffect(() => {
    return this.actions$.pipe(
      ofType(DecisionActions.dobaviOdlukuTrecaFaza.type),
      switchMap((action) => {
        return this.httpService
          .proveriOdlukuTrecaFaza(action.nihhs)
          .pipe(map((odluka) => {
            if (odluka.status === DecisionStatus.ODBIJENA) this.notifierService.notifyErrorMessage("Primena trombolize odbijena");
            else this.notifierService.notifySuccess("Pacijent je podoban za primenu trombolize");
            return DecisionActions.setOdluka({odluka: odluka})
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
