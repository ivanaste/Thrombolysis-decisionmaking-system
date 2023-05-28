import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DecisionsComponent} from "./components/decisions/decisions.component";
import {DecisionsTableComponent} from "./components/decisions-table/decisions-table.component";
import {DecisionsResolver} from "./resolvers/decisions.resolver";
import {OdlukaOTromboliziComponent} from "./components/odluka-o_trombolizi/odluka-otrombolizi.component";

const routes: Routes = [
  {
    path: '',
    component: DecisionsComponent,
    children: [
      {
        path: '',
        component: DecisionsTableComponent,
        resolve: [DecisionsResolver]
      },
      {
        path: 'trenutakNastanka',
        component: OdlukaOTromboliziComponent,
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DecisionsRoutingModule {
}
