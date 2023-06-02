import { Component, OnDestroy, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { selectRole } from '../../auth/store/auth.selectors';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss'],
})
export class HomePageComponent implements OnInit, OnDestroy {
  storeSubscription!: Subscription;
  userRole: string | null = null;

  constructor(private store: Store) {}

  ngOnInit(): void {
    this.storeSubscription = this.store.select(selectRole).subscribe((role) => {
      this.userRole = role;
    });
  }

  ngOnDestroy(): void {
    this.storeSubscription?.unsubscribe();
  }
}
