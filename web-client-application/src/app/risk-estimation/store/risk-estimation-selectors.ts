import {createFeatureSelector, createSelector} from "@ngrx/store";
import {State} from "./risk-estimation-reducer";

export const selectRiskEstimationState = createFeatureSelector<State>('riskEstimations');

export const selectRiskEstimations = createSelector(
  selectRiskEstimationState,
  (state) => state.riskEstimations
);
