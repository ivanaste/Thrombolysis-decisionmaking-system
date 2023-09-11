import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OnsetOfSymptomsComponent } from './onset-of-symptoms.component';

describe('TrenutakNastankaSimptomaComponent', () => {
  let component: OnsetOfSymptomsComponent;
  let fixture: ComponentFixture<OnsetOfSymptomsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OnsetOfSymptomsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OnsetOfSymptomsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
