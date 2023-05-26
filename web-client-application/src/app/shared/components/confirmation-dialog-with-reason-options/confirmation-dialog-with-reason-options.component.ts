import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-confirmation-dialog-with-reason-options',
  templateUrl: './confirmation-dialog-with-reason-options.component.html',
  styleUrls: ['./confirmation-dialog-with-reason-options.component.scss'],
})
export class ConfirmationDialogWithReasonOptionsComponent {
  form: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA)
    public data: {
      title: string;
      text: string;
      options: string[];
      addCustom: boolean;
    },
    private formBuilder: FormBuilder
  ) {
    this.form = this.formBuilder.group({
      selectedOption: [this.data.options[0]],
      customOption: [''],
    });
  }

  get selectedOption() {
    return this.form.get('selectedOption');
  }

  hasOptions() {
    return this.data && this.data.options && this.data.options.length > 0;
  }

  getSelectedOption(): string {
    if (this.selectedOption?.value === 'custom') {
      return this.form.get('customOption')?.value;
    }
    return this.selectedOption?.value;
  }
}
