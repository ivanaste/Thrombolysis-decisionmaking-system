import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {DecisionsRoutingModule} from './decisions-routing.module';
import {StoreModule} from '@ngrx/store';
import * as fromDecisions from './store/decisions-reducer';
import {DecisionsTableComponent} from './components/decisions-table/decisions-table.component';
import {DecisionsComponent} from './components/decisions/decisions.component';
import {SharedModule} from "../shared/shared.module";
import {NgxMatTimepickerModule} from "ngx-mat-timepicker";
import {MatRadioModule} from "@angular/material/radio";
import {
  TrenutakNastankaSimptomaComponent
} from "./components/odluka-o_trombolizi/trenutak-nastanka-simptoma/trenutak-nastanka-simptoma.component";
import {
  NeuroloskiPregledComponent
} from "./components/odluka-o_trombolizi/neuroloski-pregled/neuroloski-pregled.component";
import {NIHHSSkorComponent} from "./components/odluka-o_trombolizi/nihhsskor/nihhsskor.component";
import {OdlukaOTromboliziComponent} from "./components/odluka-o_trombolizi/odluka-otrombolizi.component";
import {ArchwizardModule} from "angular-archwizard";


@NgModule({
  declarations: [
    DecisionsTableComponent,
    DecisionsComponent,
    OdlukaOTromboliziComponent,
    TrenutakNastankaSimptomaComponent,
    NeuroloskiPregledComponent,
    NIHHSSkorComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    DecisionsRoutingModule,
    StoreModule.forFeature('decisions', fromDecisions.reducer),
    NgxMatTimepickerModule,
    MatRadioModule,
    ArchwizardModule
  ],
})
export class DecisionsModule {
}
