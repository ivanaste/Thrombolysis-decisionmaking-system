import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {RiskEstimationRoutingModule} from './risk-estimation-routing.module';
import {StoreModule} from '@ngrx/store';
import * as fromDecisions from './store/risk-estimation-reducer';
import {RiskEstimationTableComponent} from './components/risk-estimation-table/risk-estimation-table.component';
import {RiskEstimationComponent} from './components/risk-estimation/risk-estimation.component';
import {SharedModule} from "../shared/shared.module";
import {NgxMatTimepickerModule} from "ngx-mat-timepicker";
import {MatRadioModule} from "@angular/material/radio";
import {ProcenaRizikaOdMUComponent} from "./components/procena-rizika-od-mu/procena-rizika-od-mu.component";


@NgModule({
  declarations: [
    RiskEstimationTableComponent,
    RiskEstimationComponent,
    ProcenaRizikaOdMUComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    RiskEstimationRoutingModule,
    NgxMatTimepickerModule,
    MatRadioModule,
    StoreModule.forFeature('riskEstimations', fromDecisions.reducer)
  ]
})
export class RiskEstimationModule {
}
