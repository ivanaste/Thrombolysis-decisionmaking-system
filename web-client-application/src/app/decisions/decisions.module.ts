import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {DecisionsRoutingModule} from './decisions-routing.module';
import {StoreModule} from '@ngrx/store';
import * as fromDecisions from './store/decisions-reducer';
import {DecisionsTableComponent} from './components/decisions-table/decisions-table.component';
import {DecisionsComponent} from './components/decisions/decisions.component';
import {SharedModule} from "../shared/shared.module";
import {CapitalizedLowerCasePipe} from './pipes/capitalized-lower-case.pipe';


@NgModule({
  declarations: [
    DecisionsTableComponent,
    DecisionsComponent,
    CapitalizedLowerCasePipe,
  ],
  imports: [
    CommonModule,
    SharedModule,
    DecisionsRoutingModule,
    StoreModule.forFeature('decisions', fromDecisions.reducer)
  ]
})
export class DecisionsModule {
}
