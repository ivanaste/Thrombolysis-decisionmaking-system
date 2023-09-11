import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DecisionFormComponent } from './decision-form.component';

describe('OdlukaOTromboliziComponent', () => {
  let component: DecisionFormComponent;
  let fixture: ComponentFixture<DecisionFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DecisionFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DecisionFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
