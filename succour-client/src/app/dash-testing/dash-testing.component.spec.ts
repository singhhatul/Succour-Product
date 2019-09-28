import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashTestingComponent } from './dash-testing.component';

describe('DashTestingComponent', () => {
  let component: DashTestingComponent;
  let fixture: ComponentFixture<DashTestingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashTestingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashTestingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
