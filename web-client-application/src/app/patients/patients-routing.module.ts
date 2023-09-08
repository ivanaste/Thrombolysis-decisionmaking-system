import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from "../auth/guards/auth.guard";
import {PatientsTableComponent} from "./components/patients-table/patients-table.component";
import {PatientsComponent} from "./components/patients/patients.component";

const routes: Routes = [
  {
    path: '',
    component: PatientsComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: PatientsTableComponent,
        canActivate: [AuthGuard],
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PatientsRoutingModule {
}
