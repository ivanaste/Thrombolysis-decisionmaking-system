import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OdlukaOTromboliziComponent } from './odluka-otrombolizi.component';

describe('OdlukaOTromboliziComponent', () => {
  let component: OdlukaOTromboliziComponent;
  let fixture: ComponentFixture<OdlukaOTromboliziComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OdlukaOTromboliziComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OdlukaOTromboliziComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
