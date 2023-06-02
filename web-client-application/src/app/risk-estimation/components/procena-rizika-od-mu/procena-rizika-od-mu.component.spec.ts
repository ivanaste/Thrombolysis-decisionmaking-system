import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcenaRizikaOdMUComponent } from './procena-rizika-od-mu.component';

describe('ProcenaRizikaOdMUComponent', () => {
  let component: ProcenaRizikaOdMUComponent;
  let fixture: ComponentFixture<ProcenaRizikaOdMUComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProcenaRizikaOdMUComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProcenaRizikaOdMUComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
