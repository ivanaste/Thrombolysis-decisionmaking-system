import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormNavigationComponent } from './components/form-navigation/form-navigation.component';
import { ConfirmationDialogComponent } from './components/confirmation-dialog/confirmation-dialog.component';
import { ConfirmationDialogWithReasonOptionsComponent } from './components/confirmation-dialog-with-reason-options/confirmation-dialog-with-reason-options.component';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, MaterialModule],
  exports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FormNavigationComponent,
  ],
  declarations: [
    FormNavigationComponent,
    ConfirmationDialogComponent,
    ConfirmationDialogWithReasonOptionsComponent,
  ],
})
export class SharedModule {}
