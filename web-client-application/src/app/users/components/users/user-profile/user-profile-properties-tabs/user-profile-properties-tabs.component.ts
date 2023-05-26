import { Component, Input } from '@angular/core';
import { Property } from '../../../../../shared/model/property';
import { User } from '../../../../../shared/model/user.model';

@Component({
  selector: 'app-user-profile-properties-tabs',
  templateUrl: './user-profile-properties-tabs.component.html',
  styleUrls: ['./user-profile-properties-tabs.component.scss'],
})
export class UserProfilePropertiesTabsComponent {
  @Input() user!: User;
  @Input() properties!: Property[];

  get firstTabLabel(): string {
    switch (this.user.role) {
      case 'TENANT':
        return 'Properties';
      default:
        return 'Owned Properties';
    }
  }

  get myProperties(): Property[] {
    switch (this.user.role) {
      case 'TENANT':
        return this.properties;
      default:
        return this.properties.filter(
          (property) => property.owner.id === this.user.id
        );
    }
  }

  get guestProperties(): Property[] {
    return this.properties.filter(
      (property) => property.owner.id !== this.user.id
    );
  }
}
