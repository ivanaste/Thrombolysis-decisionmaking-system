import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RiskEstimationComponent} from "./components/risk-estimation/risk-estimation.component";
import {RiskEstimationTableComponent} from "./components/risk-estimation-table/risk-estimation-table.component";
import {RiskEstimationResolver} from "./resolvers/risk-estimation.resolver";
import {ProcenaRizikaOdMUComponent} from "./components/procena-rizika-od-mu/procena-rizika-od-mu.component";
import {AuthGuard} from "../auth/guards/auth.guard";

const routes: Routes = [
  {
    path: '',
    component: RiskEstimationComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: RiskEstimationTableComponent,
        canActivate: [AuthGuard],
        resolve: [RiskEstimationResolver]
      },
      {
        path: 'procenaRizikaOdMU/:jmbg/:datumRodjenja',
        canActivate: [AuthGuard],
        component: ProcenaRizikaOdMUComponent,
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
