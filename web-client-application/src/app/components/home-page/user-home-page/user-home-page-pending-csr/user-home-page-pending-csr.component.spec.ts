import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserHomePagePendingCsrComponent } from './user-home-page-pending-csr.component';

describe('UserHomePagePendingCsrComponent', () => {
  let component: UserHomePagePendingCsrComponent;
  let fixture: ComponentFixture<UserHomePagePendingCsrComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserHomePagePendingCsrComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserHomePagePendingCsrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
