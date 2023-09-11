import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NavbarComponent} from './core/components/navbar/navbar.component';
import {SharedModule} from './shared/shared.module';
import {HttpClientModule} from '@angular/common/http';
import {CoreModule} from './core/core.module';
import {ToastrModule} from 'ngx-toastr';
import {ArchwizardModule} from "angular-archwizard";
import {NgxMatTimepickerModule} from "ngx-mat-timepicker";
import {MatDatepickerModule} from '@angular/material/datepicker';
@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
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
