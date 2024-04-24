import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BetsComponent } from './bets.component';

describe('BetsComponent', () => {
  let component: BetsComponent;
  let fixture: ComponentFixture<BetsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BetsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
