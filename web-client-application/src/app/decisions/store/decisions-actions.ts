import {createAction, props, union} from "@ngrx/store";
import {Decision} from "../model/decision";
import {TrenutakNastanka} from "../model/TrenutakNastanka";
import {StatusOdluke} from "../model/StatusOdluke";


export const getDecisions = createAction('[Decisions] Get Decisions');
export const dobaviOdlukuPrvaFaza = createAction(
  '[Decisions] Odluka Prva Faza', props<{ trenutakNastanka: TrenutakNastanka }>()
);

export const setOdluka = createAction(
  '[Decisions] Set Odluka', props<{ odluka: Decision }>()
);
export const setStatusOdluke = createAction(
  '[Decisions] Set Status Odluke', props<{ status: StatusOdluke }>()
);
export const setDecisions = createAction(
  '[Decisions] Set Decisions',
  props<{ decisions: Decision[] }>()
);

const all = union({
  getDecisions,
  setDecisions,
  dobaviOdlukuPrvaFaza,
  setStatusOdluke
});

export type DecisionsActionsUnion = typeof all;
