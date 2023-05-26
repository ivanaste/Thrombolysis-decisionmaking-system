import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmationDialogWithReasonOptionsComponent } from './confirmation-dialog-with-reason-options.component';

describe('ConfirmationDialogWithReasonOptionsComponent', () => {
  let component: ConfirmationDialogWithReasonOptionsComponent;
  let fixture: ComponentFixture<ConfirmationDialogWithReasonOptionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfirmationDialogWithReasonOptionsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfirmationDialogWithReasonOptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
