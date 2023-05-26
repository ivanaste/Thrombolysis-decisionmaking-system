import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { autoLogin } from './auth/store/auth.actions';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  title = 'TDS';

  constructor(
    private store: Store,
    private matIconRegistry: MatIconRegistry,
    private domSanitizer: DomSanitizer
  ) {
    matIconRegistry.addSvgIcon(
      'hidePassword',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/password_visibility/visibility_off.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'showPassword',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/password_visibility/visibility.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'search',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/search/search.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'filters',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/search/filters.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'account-avatar',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/user/account_avatar.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'edit',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/user/edit.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'property-avatar',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/property/property_avatar.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'add-member',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/property/new_member.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'members',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/property/members.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'owner',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/property/owner_mark.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'remove-member',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/property/remove_member.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'my-property',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/user/my_property.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'visible-property',
      this.domSanitizer.bypassSecurityTrustResourceUrl('../assets/user/key.svg')
    );
    matIconRegistry.addSvgIcon(
      'plus',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/user/plus.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'clear',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/user/clear.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'light',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/homepage/light.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'network',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/homepage/network.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'notification',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/homepage/notification.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'security',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/homepage/security.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'temperature',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/homepage/temperature.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'camera',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/homepage/camera.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'linkedIn',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/social media/linkedin.svg'
      )
    );
    matIconRegistry.addSvgIcon(
      'gitHub',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/social media/github.svg'
      )
    );
  }

  ngOnInit(): void {
    this.store.dispatch(autoLogin());
  }
}
