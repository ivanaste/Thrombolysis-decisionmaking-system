import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NIHHSSkorComponent } from './nihhsskor.component';

describe('NIHHSSkorComponent', () => {
  let component: NIHHSSkorComponent;
  let fixture: ComponentFixture<NIHHSSkorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NIHHSSkorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NIHHSSkorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
