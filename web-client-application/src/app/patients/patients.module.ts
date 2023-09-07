import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {PatientsRoutingModule} from './patients-routing.module';
import {PatientsTableComponent} from './components/patients-table/patients-table.component';
import {SharedModule} from "../shared/shared.module";
import {NgxMatTimepickerModule} from "ngx-mat-timepicker";
import {MatRadioModule} from "@angular/material/radio";
import {PatientsComponent} from './components/patients/patients.component';


@NgModule({
  declarations: [
    PatientsTableComponent,
    PatientsComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    NgxMatTimepickerModule,
    MatRadioModule,
    PatientsRoutingModule
  ]
})
export class PatientsModule {
}
