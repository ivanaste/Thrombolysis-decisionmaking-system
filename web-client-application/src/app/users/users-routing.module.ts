import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UsersComponent} from './components/users/users.component';
import {AllUsersTableComponent} from './components/users/all-users-table/all-users-table.component';
import {UsersResolver} from './resolvers/users.resolver';
import {UserProfileFormComponent} from './components/users/user-profile-form/user-profile-form.component';
import {OdlukaOTromboliziComponent} from "../decisions/components/odluka-o_trombolizi/odluka-otrombolizi.component";
import {ProcenaRizikaOdMUComponent} from "../decisions/components/procena-rizika-od-mu/procena-rizika-od-mu.component";

const routes: Routes = [
  {
    path: '',
    component: UsersComponent,
    children: [
      {
        path: 'all',
        component: AllUsersTableComponent,
        resolve: [UsersResolver],
      },
      {
        path: 'new',
        component: UserProfileFormComponent,
      },
      {
        path: 'edit/:id',
        component: UserProfileFormComponent,
      },
      {
        path: 'procenaRizikaOdMU',
        component: ProcenaRizikaOdMUComponent,
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UsersRoutingModule {
}
