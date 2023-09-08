import { NgModule } from '@angular/core';
import { LoginFormComponent } from './components/auth/login-form/login-form.component';
import { AuthComponent } from './components/auth/auth.component';
import { SharedModule } from '../shared/shared.module';
import { AuthRoutingModule } from './auth-routing.module';
import { StoreModule } from '@ngrx/store';
import * as fromAuth from './store/auth.reducer';
import { SignupFormComponent } from './components/auth/signup-form/signup-form.component';

@NgModule({
  declarations: [
    LoginFormComponent,
    AuthComponent,
    SignupFormComponent,
  ],
  imports: [
    AuthRoutingModule,
    SharedModule,
    StoreModule.forFeature('auth', fromAuth.reducer),
  ],
})
export class AuthModule {}
