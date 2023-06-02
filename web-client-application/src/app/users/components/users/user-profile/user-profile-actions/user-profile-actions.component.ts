import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-user-profile-actions',
  templateUrl: './user-profile-actions.component.html',
  styleUrls: ['./user-profile-actions.component.scss'],
})
export class UserProfileActionsComponent {
  @Input() userId!: string;
}
