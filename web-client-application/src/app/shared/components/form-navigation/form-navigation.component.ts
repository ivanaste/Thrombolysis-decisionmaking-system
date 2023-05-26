import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormNavigationStep } from '../../model/form-navigation-step.model';

@Component({
  selector: 'app-form-navigation',
  templateUrl: './form-navigation.component.html',
  styleUrls: ['./form-navigation.component.scss'],
})
export class FormNavigationComponent implements OnInit {
  @Input() steps?: FormNavigationStep[];
  @Output() stepClicked = new EventEmitter<number>();
  progressWidth = 0;

  ngOnInit(): void {
    if (!this.steps) {
      throw new TypeError("The Input 'steps' is required");
    }
  }

  onStepClick(index: number) {
    this.steps?.forEach((step) => (step.active = false));
    if (this.steps) this.steps[index].active = true;
    this.updateProgressWidth();
    this.stepClicked.emit(index);
  }

  updateProgressWidth() {
    const activeIndex = this.steps?.findIndex((step) => step.active);
    if (this.steps && activeIndex !== undefined)
      this.progressWidth = (activeIndex / (this.steps.length - 1)) * 70;
  }
}
