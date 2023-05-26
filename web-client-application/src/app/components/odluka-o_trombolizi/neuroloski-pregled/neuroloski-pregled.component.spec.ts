import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeuroloskiPregledComponent } from './neuroloski-pregled.component';

describe('NeuroloskiPregledComponent', () => {
  let component: NeuroloskiPregledComponent;
  let fixture: ComponentFixture<NeuroloskiPregledComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NeuroloskiPregledComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NeuroloskiPregledComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
