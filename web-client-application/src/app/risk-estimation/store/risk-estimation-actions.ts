import {createAction, props, union} from "@ngrx/store";
import {RiskEstimation} from "../model/risk-estimation";
import {ProcenaRizikaOdMURequest} from "../model/procena-rizika-od-mu";
import {NivoRizikaMu} from "../model/nivo-rizika-mu";


export const getRiskEstimations = createAction('[Risk Estimation] Get Risk Estimations');

export const setRiskEstimations = createAction(
  '[Risk Estimation] Set Risk Estimations',
  props<{ riskEstimations: RiskEstimation[] }>()
);

export const calculateRisk = createAction(
  '[Risk Estimation] Estimate Risk', props<{
    request: ProcenaRizikaOdMURequest
  }>());

export const setRisk = createAction(
  '[Risk Estimation] Set Risk', props<{
    rizik: NivoRizikaMu
  }>());

const all = union({
  getRiskEstimations: getRiskEstimations,
  setRiskEstimations: setRiskEstimations,
  calculateRisk: calculateRisk,
  setRisk: setRisk,
});

export type RiskEstimationActionsUnion = typeof all;
