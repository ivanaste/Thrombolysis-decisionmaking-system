import {createAction, props, union} from "@ngrx/store";
import {RiskEstimation} from "../model/risk-estimation";


export const getRiskEstimations = createAction('[Risk Estimation] Get Risk Estimations');

export const setRiskEstimations = createAction(
  '[Risk Estimation] Set Risk Estimations',
  props<{ riskEstimations: RiskEstimation[] }>()
);

const all = union({
  getRiskEstimations: getRiskEstimations,
  setRiskEstimations: setRiskEstimations,
});

export type RiskEstimationActionsUnion = typeof all;
