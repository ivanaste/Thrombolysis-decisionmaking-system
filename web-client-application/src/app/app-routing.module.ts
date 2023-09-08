import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/auth/login',
  },
  {
    path: 'auth',
    loadChildren: () =>
      import('./auth/auth.module').then((module) => module.AuthModule),
  },
  {
    path: 'password',
    loadChildren: () =>
      import('./password/password.module').then(
        (module) => module.PasswordModule
      ),
  },
  {
    path: 'decisions',
    loadChildren: () =>
      import('./decisions/decisions.module').then((module) => module.DecisionsModule),
    canActivate: [],
  },
  {
    path: 'risk-estimations',
    loadChildren: () =>
      import('./risk-estimation/risk-estimation.module').then((module) => module.RiskEstimationModule),
    canActivate: [],
  },
  {
    path: 'patients',
    loadChildren: () =>
      import('./patients/patients.module').then((module) => module.PatientsModule),
    canActivate: [],
  },
  {
    path: 'workers',
    loadChildren: () =>
      import('./workers/workers.module').then((module) => module.WorkersModule),
    canActivate: [],
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules}),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
