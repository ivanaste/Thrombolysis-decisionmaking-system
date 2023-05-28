import {Action, createReducer, on} from '@ngrx/store';
import * as RiskEstimationActions from "./risk-estimation-actions";
import {RiskEstimation} from "../model/risk-estimation";
import {NivoRizikaMu} from "../model/nivo-rizika-mu";

export interface State {
  riskEstimations: RiskEstimation[];
  rizik: NivoRizikaMu | null;
}

const initialState: State = {
  riskEstimations: [],
  rizik: null,
};

const riskEstimationReducer = createReducer(
  initialState,
  on(RiskEstimationActions.setRiskEstimations, (state, {riskEstimations}) => ({
    ...state,
    riskEstimations: riskEstimations,
  })),
  on(RiskEstimationActions.setRisk, (state, {rizik}) => ({
    ...state,
    rizik: rizik,
  })),
);

export function reducer(state: State | undefined, action: Action) {
  return riskEstimationReducer(state, action);
}
