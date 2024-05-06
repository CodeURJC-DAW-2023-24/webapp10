import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BetsadminComponent } from './betsadmin.component';

describe('BetsadminComponent', () => {
  let component: BetsadminComponent;
  let fixture: ComponentFixture<BetsadminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BetsadminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BetsadminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
