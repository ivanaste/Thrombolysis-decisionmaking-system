import { Component, Input } from '@angular/core';
import { User } from '../../../../../shared/model/user.model';

@Component({
  selector: 'app-user-profile-info',
  templateUrl: './user-profile-info.component.html',
  styleUrls: ['./user-profile-info.component.scss'],
})
export class UserProfileInfoComponent {
  @Input() user!: User;
  @Input() isAdminLogged!: boolean;
}
