import {Action, createReducer, on} from '@ngrx/store';
import * as DecisionActions from "../store/decisions-actions";
import {Decision} from "../model/decision";
import {StatusOdluke} from "../model/StatusOdluke";

export interface State {
  decisions: Decision[];
  statusOdluke: StatusOdluke
}

const initialState: State = {
  decisions: [],
  statusOdluke: StatusOdluke.U_PROCESU
};

const decisionsReducer = createReducer(
  initialState,
  on(DecisionActions.setDecisions, (state, {decisions}) => ({
    ...state,
    decisions: decisions,
  })),
  on(DecisionActions.setStatusOdluke, (state, {status}) => ({
    ...state,
    statusOdluke: status,
  })),
);

export function reducer(state: State | undefined, action: Action) {
  return decisionsReducer(state, action);
}
