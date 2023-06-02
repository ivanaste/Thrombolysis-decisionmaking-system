import {createFeatureSelector, createSelector} from "@ngrx/store";
import {State} from "./decisions-reducer";

export const selectDecisionsState = createFeatureSelector<State>('decisions');

export const selectDecisions = createSelector(
  selectDecisionsState,
  (state) => state.decisions
);

export const selectOdluka = createSelector(
  selectDecisionsState,
  (state) => state.odluka
);
