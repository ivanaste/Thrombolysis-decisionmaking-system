import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PasswordComponent } from './components/password/password.component';
import { SetPasswordFormComponent } from './components/password/set-password-form/set-password-form.component';

const routes: Routes = [
  {
    path: '',
    component: PasswordComponent,
    children: [
      {
        path: 'change/:authToken',
        component: SetPasswordFormComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PasswordRoutingModule {}
