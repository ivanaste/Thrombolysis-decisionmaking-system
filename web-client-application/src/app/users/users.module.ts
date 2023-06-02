import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { StoreModule } from '@ngrx/store';
import * as fromUsers from './store/users.reducer';
import { UsersComponent } from './components/users/users.component';
import { UsersRoutingModule } from './users-routing.module';
import { AllUsersTableComponent } from './components/users/all-users-table/all-users-table.component';
import { UserProfileComponent } from './components/users/user-profile/user-profile.component';
import { UserProfileFormComponent } from './components/users/user-profile-form/user-profile-form.component';
import { UserProfileInfoComponent } from './components/users/user-profile/user-profile-info/user-profile-info.component';
import { UserPropertyCardComponent } from './components/users/user-profile/user-profile-properties-tabs/user-property-card/user-property-card.component';
import { UserProfileActionsComponent } from './components/users/user-profile/user-profile-actions/user-profile-actions.component';
import { UserProfilePropertiesTabsComponent } from './components/users/user-profile/user-profile-properties-tabs/user-profile-properties-tabs.component';

@NgModule({
  declarations: [
    UsersComponent,
    AllUsersTableComponent,
    UserProfileComponent,
    UserProfileFormComponent,
    UserProfileInfoComponent,
    UserPropertyCardComponent,
    UserProfileActionsComponent,
    UserProfilePropertiesTabsComponent,
  ],
  imports: [
    SharedModule,
    StoreModule.forFeature('users', fromUsers.reducer),
    UsersRoutingModule,
  ],
})
export class UsersModule {}
