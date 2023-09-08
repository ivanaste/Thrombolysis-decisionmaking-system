import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {WorkersRoutingModule} from './workers-routing.module';
import {WorkersTableComponent} from './components/workers/workers-table/workers-table.component';
import {SharedModule} from "../shared/shared.module";
import {NgxMatTimepickerModule} from "ngx-mat-timepicker";
import {MatRadioModule} from "@angular/material/radio";
import {WorkersComponent} from './components/workers/workers.component';


@NgModule({
  declarations: [
    WorkersTableComponent,
    WorkersComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    NgxMatTimepickerModule,
    MatRadioModule,
    WorkersRoutingModule
  ]
})
export class WorkersModule {
}
