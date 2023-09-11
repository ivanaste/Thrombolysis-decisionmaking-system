import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {TherapyDecisionRoutingModule} from './therapy-decision-routing.module';
import {StoreModule} from '@ngrx/store';
import * as fromDecisions from './store/decisions-reducer';
import {DecisionsTableComponent} from './components/decision/decisions-table/decisions-table.component';
import {DecisionComponent} from './components/decision/decision.component';
import {SharedModule} from "../shared/shared.module";
import {NgxMatTimepickerModule} from "ngx-mat-timepicker";
import {MatRadioModule} from "@angular/material/radio";
import {
  OnsetOfSymptomsComponent
} from "./components/decision/decision-form/onset-of-symptoms/onset-of-symptoms.component";
import {
  NeuroExaminationComponent
} from "./components/decision/decision-form/neuro-examination/neuro-examination.component";
import {NIHHSSkorComponent} from "./components/decision/decision-form/nihhsScore/nihhsskor.component";
import {DecisionFormComponent} from "./components/decision/decision-form/decision-form.component";
import {ArchwizardModule} from "angular-archwizard";


@NgModule({
  declarations: [
    DecisionsTableComponent,
    DecisionComponent,
    DecisionFormComponent,
    OnsetOfSymptomsComponent,
    NeuroExaminationComponent,
    NIHHSSkorComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    TherapyDecisionRoutingModule,
    StoreModule.forFeature('decisions', fromDecisions.reducer),
    NgxMatTimepickerModule,
    MatRadioModule,
    ArchwizardModule,
  ],
})
export class TherapyDecisionModule {
}
