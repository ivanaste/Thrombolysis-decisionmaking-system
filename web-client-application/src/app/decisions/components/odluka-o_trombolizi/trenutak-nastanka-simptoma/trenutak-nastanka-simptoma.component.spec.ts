import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrenutakNastankaSimptomaComponent } from './trenutak-nastanka-simptoma.component';

describe('TrenutakNastankaSimptomaComponent', () => {
  let component: TrenutakNastankaSimptomaComponent;
  let fixture: ComponentFixture<TrenutakNastankaSimptomaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TrenutakNastankaSimptomaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TrenutakNastankaSimptomaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
