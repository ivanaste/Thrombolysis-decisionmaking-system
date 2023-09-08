import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from "../auth/guards/auth.guard";
import {WorkersTableComponent} from "./components/workers/workers-table/workers-table.component";
import {WorkersComponent} from "./components/workers/workers.component";

const routes: Routes = [
  {
    path: '',
    component: WorkersComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: WorkersTableComponent,
        canActivate: [AuthGuard],
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WorkersRoutingModule {
}
