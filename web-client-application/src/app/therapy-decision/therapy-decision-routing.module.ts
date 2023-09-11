import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DecisionComponent} from "./components/decision/decision.component";
import {DecisionsTableComponent} from "./components/decision/decisions-table/decisions-table.component";
import {DecisionsResolver} from "./resolvers/decisions.resolver";
import {AuthGuard} from "../auth/guards/auth.guard";
import {DecisionFormComponent} from "./components/decision/decision-form/decision-form.component";

const routes: Routes = [
  {
    path: '',
    component: DecisionComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        canActivate: [AuthGuard],
        component: DecisionsTableComponent,
        resolve: [DecisionsResolver]
      },
      {
        path: 'trenutakNastanka',
        component: DecisionFormComponent,
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TherapyDecisionRoutingModule {
}
