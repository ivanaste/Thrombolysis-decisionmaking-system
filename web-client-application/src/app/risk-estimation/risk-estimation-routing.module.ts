import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RiskEstimationComponent} from "./components/risk-estimation/risk-estimation.component";
import {RiskEstimationTableComponent} from "./components/risk-estimation-table/risk-estimation-table.component";
import {RiskEstimationResolver} from "./resolvers/risk-estimation.resolver";

const routes: Routes = [
  {
    path: '',
    component: RiskEstimationComponent,
    children: [
      {
        path: '',
        component: RiskEstimationTableComponent,
        resolve: [RiskEstimationResolver]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RiskEstimationRoutingModule {
}
