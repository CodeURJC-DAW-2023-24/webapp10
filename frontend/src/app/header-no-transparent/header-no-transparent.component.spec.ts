import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderNoTransparentComponent } from './header-no-transparent.component';

describe('HeaderNoTransparentComponent', () => {
  let component: HeaderNoTransparentComponent;
  let fixture: ComponentFixture<HeaderNoTransparentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderNoTransparentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HeaderNoTransparentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
