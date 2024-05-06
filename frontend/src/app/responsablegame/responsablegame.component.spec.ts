import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResponsablegameComponent } from './responsablegame.component';

describe('ResponsablegameComponent', () => {
  let component: ResponsablegameComponent;
  let fixture: ComponentFixture<ResponsablegameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResponsablegameComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ResponsablegameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
