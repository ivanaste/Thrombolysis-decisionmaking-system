import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RiskEstimationComponent} from "./components/risk-estimation/risk-estimation.component";
import {RiskEstimationTableComponent} from "./components/risk-estimation-table/risk-estimation-table.component";
import {RiskEstimationResolver} from "./resolvers/risk-estimation.resolver";
import {RiskEstimationFormComponent} from "./components/risk-estimation-form/risk-estimation-form.component";
import {AuthGuard} from "../auth/guards/auth.guard";

const routes: Routes = [
  {
    path: '',
    component: RiskEstimationFormComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: RiskEstimationTableComponent,
        canActivate: [AuthGuard],
        resolve: [RiskEstimationResolver]
      },
      {
        path: 'procenaRizikaOdMU',
        canActivate: [AuthGuard],
        component: RiskEstimationFormComponent,
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RiskEstimationRoutingModule {
}
