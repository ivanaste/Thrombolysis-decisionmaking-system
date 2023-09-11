import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MaterialModule} from '../material/material.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CapitalizedLowerCasePipe} from "./pipes/capitalized-lower-case.pipe";

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, MaterialModule],
  exports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    CapitalizedLowerCasePipe,
  ],
  declarations: [
    CapitalizedLowerCasePipe
  ],
})
export class SharedModule {
}
