import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RotaListComponent } from './rota-list.component';

describe('RotaList', () => {
  let component: RotaListComponent;
  let fixture: ComponentFixture<RotaListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RotaListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RotaListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
