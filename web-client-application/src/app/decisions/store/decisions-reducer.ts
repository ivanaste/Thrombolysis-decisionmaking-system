import {Action, createReducer, on} from '@ngrx/store';
import * as DecisionActions from "../store/decisions-actions";
import {Decision} from "../model/decision";

export interface State {
  decisions: Decision[];
}

const initialState: State = {
  decisions: [],
};

const decisionsReducer = createReducer(
  initialState,
  on(DecisionActions.setDecisions, (state, {decisions}) => ({
    ...state,
    decisions: decisions,
  })),
);

export function reducer(state: State | undefined, action: Action) {
  return decisionsReducer(state, action);
}
