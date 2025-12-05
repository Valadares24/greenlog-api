import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CaminhaoCrud } from './caminhao-crud';

describe('CaminhaoCrud', () => {
  let component: CaminhaoCrud;
  let fixture: ComponentFixture<CaminhaoCrud>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CaminhaoCrud]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CaminhaoCrud);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
