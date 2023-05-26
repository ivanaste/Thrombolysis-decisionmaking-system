import { Component, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { Store } from '@ngrx/store';

@Component({
  selector: 'app-user-home-page',
  templateUrl: './user-home-page.component.html',
  styleUrls: ['./user-home-page.component.scss'],
})
export class UserHomePageComponent implements OnDestroy {
  storeSubscription!: Subscription;

  constructor(private store: Store) {}

  ngOnDestroy(): void {
    this.storeSubscription?.unsubscribe();
  }
}
