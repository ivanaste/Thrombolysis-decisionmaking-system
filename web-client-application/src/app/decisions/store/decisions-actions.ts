import {createAction, props, union} from "@ngrx/store";
import {Decision} from "../model/decision";
import {TrenutakNastanka} from "../model/TrenutakNastanka";
import {DecisionStatus} from "../model/decision-status";
import {NeuroloskiPregled} from "../model/NeuroloskiPregled";
import {NIHHS} from "../model/NIHHS";

export const getDecisions = createAction('[Decisions] Get Decisions');
export const dobaviOdlukuPrvaFaza = createAction(
  '[Decisions] Odluka Prva Faza', props<{ trenutakNastanka: TrenutakNastanka }>()
);

export const dobaviOdlukuDrugaFaza = createAction(
  '[Decisions] Odluka Druga Faza', props<{ neuroloskiPregled: NeuroloskiPregled }>()
);

export const dobaviOdlukuTrecaFaza = createAction(
  '[Decisions] Odluka Treca Faza', props<{ nihhs: NIHHS }>()
);

export const setOdluka = createAction(
  '[Decisions] Set Odluka', props<{ odluka: Decision }>()
);
export const setStatusOdluke = createAction(
  '[Decisions] Set Status Odluke', props<{ status: DecisionStatus }>()
);
export const setDecisions = createAction(
  '[Decisions] Set Decisions',
  props<{ decisions: Decision[] }>()
);

const all = union({
  getDecisions,
  setDecisions,
  dobaviOdlukuPrvaFaza,
  dobaviOdlukuDrugaFaza,
  dobaviOdlukuTrecaFaza,
  setStatusOdluke
});

export type DecisionsActionsUnion = typeof all;
