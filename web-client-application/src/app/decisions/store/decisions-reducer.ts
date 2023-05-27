import {Action, createReducer, on} from '@ngrx/store';
import * as DecisionActions from "../store/decisions-actions";
import {Decision} from "../model/decision";
import {DecisionStatus} from "../model/decision-status";

export interface State {
  decisions: Decision[];
  odluka: Decision
}

const initialState: State = {
  decisions: [],
  odluka: {
    id: '',
    status: DecisionStatus.U_PROCESU
  }
};

const decisionsReducer = createReducer(
  initialState,
  on(DecisionActions.setDecisions, (state, {decisions}) => ({
    ...state,
    decisions: decisions,
  })),
  // on(DecisionActions.setStatusOdluke, (state, {status}) => ({
  //   ...state,
  //   statusOdluke: status,
  // })),
  on(DecisionActions.setOdluka, (state, {odluka}) => ({
    ...state,
    odluka: odluka,
  })),
);

export function reducer(state: State | undefined, action: Action) {
  return decisionsReducer(state, action);
}
