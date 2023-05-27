import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NavbarComponent} from './components/navbar/navbar.component';
import {SharedModule} from './shared/shared.module';
import {HttpClientModule} from '@angular/common/http';
import {CoreModule} from './core/core.module';
import {HomePageComponent} from './components/home-page/home-page.component';
import {ToastrModule} from 'ngx-toastr';
import {WelcomingPageComponent} from './components/home-page/welcoming-page/welcoming-page.component';
import {AdminHomePageComponent} from './components/home-page/admin-home-page/admin-home-page.component';
import {UserHomePageComponent} from './components/home-page/user-home-page/user-home-page.component';
import {
  UserHomePageNoCsrComponent
} from './components/home-page/user-home-page/user-home-page-no-csr/user-home-page-no-csr.component';
import {
  UserHomePagePendingCsrComponent
} from './components/home-page/user-home-page/user-home-page-pending-csr/user-home-page-pending-csr.component';
import {
  UserHomePageCsrComponent
} from './components/home-page/user-home-page/user-home-page-csr/user-home-page-csr.component';
import {OdlukaOTromboliziComponent} from './decisions/components/odluka-o_trombolizi/odluka-otrombolizi.component';
import {ArchwizardModule} from "angular-archwizard";
import {NgxMatTimepickerModule} from "ngx-mat-timepicker";
import {
  TrenutakNastankaSimptomaComponent
} from './decisions/components/odluka-o_trombolizi/trenutak-nastanka-simptoma/trenutak-nastanka-simptoma.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { NeuroloskiPregledComponent } from './decisions/components/odluka-o_trombolizi/neuroloski-pregled/neuroloski-pregled.component';
import { NIHHSSkorComponent } from './decisions/components/odluka-o_trombolizi/nihhsskor/nihhsskor.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomePageComponent,
    WelcomingPageComponent,
    AdminHomePageComponent,
    UserHomePageComponent,
    UserHomePageNoCsrComponent,
    UserHomePagePendingCsrComponent,
    UserHomePageCsrComponent,
    OdlukaOTromboliziComponent,
    TrenutakNastankaSimptomaComponent,
    NeuroloskiPregledComponent,
    NIHHSSkorComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ArchwizardModule,
    MatDatepickerModule,
    ToastrModule.forRoot({
      positionClass: 'toast-top-center',
      progressBar: true,
      preventDuplicates: true,
      closeButton: true,
    }),
    HttpClientModule,
    SharedModule,
    CoreModule,
    AppRoutingModule,
    NgxMatTimepickerModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {
}
