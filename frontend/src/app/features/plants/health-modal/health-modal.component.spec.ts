import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HealthModalComponent } from './health-modal.component';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { HealthStatus } from '../../../core/models/health.model';

describe('HealthModalComponent', () => {
  let component: HealthModalComponent;
  let fixture: ComponentFixture<HealthModalComponent>;
  let dialogRefMock: any;

  beforeEach(async () => {
    dialogRefMock = {
      close: jasmine.createSpy('close')
    };

    await TestBed.configureTestingModule({
      imports: [HealthModalComponent, ReactiveFormsModule, NoopAnimationsModule],
      providers: [
        { provide: MatDialogRef, useValue: dialogRefMock },
        { provide: MAT_DIALOG_DATA, useValue: { plantId: 1 } }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(HealthModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should close dialog with form value on submit', () => {
    component.form.patchValue({
      healthStatus: HealthStatus.EXCELLENT,
      recordedDate: '2024-05-01',
      notes: 'Looking great!'
    });
    component.onSubmit();
    expect(dialogRefMock.close).toHaveBeenCalledWith({
      plantId: 1,
      healthStatus: HealthStatus.EXCELLENT,
      recordedDate: '2024-05-01',
      notes: 'Looking great!'
    });
  });
});
