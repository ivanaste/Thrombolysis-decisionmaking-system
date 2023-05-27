import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {DecisionsRoutingModule} from './decisions-routing.module';
import {StoreModule} from '@ngrx/store';
import * as fromDecisions from './store/decisions-reducer';
import {DecisionsTableComponent} from './components/decisions-table/decisions-table.component';
import {DecisionsComponent} from './components/decisions/decisions.component';
import {SharedModule} from "../shared/shared.module";
import {CapitalizedLowerCasePipe} from './pipes/capitalized-lower-case.pipe';
import { ProcenaRizikaOdMUComponent } from './components/procena-rizika-od-mu/procena-rizika-od-mu.component';
import {NgxMatTimepickerModule} from "ngx-mat-timepicker";
import {MatRadioModule} from "@angular/material/radio";


@NgModule({
  declarations: [
    DecisionsTableComponent,
    DecisionsComponent,
    CapitalizedLowerCasePipe,
    ProcenaRizikaOdMUComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    DecisionsRoutingModule,
    StoreModule.forFeature('decisions', fromDecisions.reducer),
    NgxMatTimepickerModule,
    MatRadioModule
  ]
})
export class DecisionsModule {
}
