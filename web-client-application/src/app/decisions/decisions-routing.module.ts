import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DecisionsComponent} from "./components/decisions/decisions.component";
import {DecisionsTableComponent} from "./components/decisions-table/decisions-table.component";
import {DecisionsResolver} from "./resolvers/decisions.resolver";

const routes: Routes = [
  {
    path: '',
    component: DecisionsComponent,
    children: [
      {
        path: '',
        component: DecisionsTableComponent,
        resolve: [DecisionsResolver]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DecisionsRoutingModule {
}
