import { NgModule } from '@angular/core';

import { PasswordRoutingModule } from './password-routing.module';
import { SharedModule } from '../shared/shared.module';
import { PasswordComponent } from './components/password/password.component';
import { SetPasswordFormComponent } from './components/password/set-password-form/set-password-form.component';

@NgModule({
  declarations: [PasswordComponent, SetPasswordFormComponent],
  imports: [SharedModule, PasswordRoutingModule],
})
export class PasswordModule {}
