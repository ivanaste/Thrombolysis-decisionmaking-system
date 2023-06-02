import { Component } from '@angular/core';

@Component({
  selector: 'app-welcoming-page',
  templateUrl: './welcoming-page.component.html',
  styleUrls: ['./welcoming-page.component.scss'],
})
export class WelcomingPageComponent {
  showSurveillance = false;
  showTemperature = false;
  showLightning = false;
  showSecurity = false;
  showNotifications = false;
  showNetwork = false;
}
