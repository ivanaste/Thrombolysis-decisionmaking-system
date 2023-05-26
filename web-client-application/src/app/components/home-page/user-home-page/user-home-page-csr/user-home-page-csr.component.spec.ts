import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserHomePageCsrComponent } from './user-home-page-csr.component';

describe('UserHomePageCsrComponent', () => {
  let component: UserHomePageCsrComponent;
  let fixture: ComponentFixture<UserHomePageCsrComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserHomePageCsrComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserHomePageCsrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
