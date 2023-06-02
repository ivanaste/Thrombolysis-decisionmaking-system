import { Component, Input } from '@angular/core';
import { Property } from '../../../../../../shared/model/property';

@Component({
  selector: 'app-user-property-card',
  templateUrl: './user-property-card.component.html',
  styleUrls: ['./user-property-card.component.scss'],
})
export class UserPropertyCardComponent {
  @Input() property!: Property;
}
