import {Action, createReducer, on} from '@ngrx/store';
import * as RiskEstimationActions from "./risk-estimation-actions";
import {RiskEstimation} from "../model/risk-estimation";

export interface State {
  riskEstimations: RiskEstimation[];
}

const initialState: State = {
  riskEstimations: [],
};

const riskEstimationReducer = createReducer(
  initialState,
  on(RiskEstimationActions.setRiskEstimations, (state, {riskEstimations}) => ({
    ...state,
    riskEstimations: riskEstimations,
  })),
);

export function reducer(state: State | undefined, action: Action) {
  return riskEstimationReducer(state, action);
}
