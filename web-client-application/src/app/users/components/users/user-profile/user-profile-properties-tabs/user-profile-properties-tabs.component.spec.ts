import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserProfilePropertiesTabsComponent } from './user-profile-properties-tabs.component';

describe('UserProfilePropertiesTabsComponent', () => {
  let component: UserProfilePropertiesTabsComponent;
  let fixture: ComponentFixture<UserProfilePropertiesTabsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserProfilePropertiesTabsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserProfilePropertiesTabsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
