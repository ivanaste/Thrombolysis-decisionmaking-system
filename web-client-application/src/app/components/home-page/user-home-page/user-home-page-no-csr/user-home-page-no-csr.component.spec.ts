import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserHomePageNoCsrComponent } from './user-home-page-no-csr.component';

describe('UserHomePageNoCsrComponent', () => {
  let component: UserHomePageNoCsrComponent;
  let fixture: ComponentFixture<UserHomePageNoCsrComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserHomePageNoCsrComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserHomePageNoCsrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
