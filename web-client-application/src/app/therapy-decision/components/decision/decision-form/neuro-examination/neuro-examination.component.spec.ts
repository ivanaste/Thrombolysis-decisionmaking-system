import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeuroExaminationComponent } from './neuro-examination.component';

describe('NeuroloskiPregledComponent', () => {
  let component: NeuroExaminationComponent;
  let fixture: ComponentFixture<NeuroExaminationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NeuroExaminationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NeuroExaminationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
