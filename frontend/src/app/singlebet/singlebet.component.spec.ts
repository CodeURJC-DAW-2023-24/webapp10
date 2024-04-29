import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SinglebetComponent } from './singlebet.component';

describe('SinglebetComponent', () => {
  let component: SinglebetComponent;
  let fixture: ComponentFixture<SinglebetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SinglebetComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SinglebetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
