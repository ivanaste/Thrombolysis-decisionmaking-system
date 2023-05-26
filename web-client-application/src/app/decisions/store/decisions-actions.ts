import {createAction, props, union} from "@ngrx/store";
import {Decision} from "../model/decision";


export const getDecisions = createAction('[Decisions] Get Decisions');

export const setDecisions = createAction(
  '[Decisions] Set Decisions',
  props<{ decisions: Decision[] }>()
);

const all = union({
  getDecisions,
  setDecisions,
});

export type DecisionsActionsUnion = typeof all;
