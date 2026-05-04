import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CareLogModalComponent } from './care-log-modal.component';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { CareType } from '../../../core/models/care.model';

describe('CareLogModalComponent', () => {
  let component: CareLogModalComponent;
  let fixture: ComponentFixture<CareLogModalComponent>;
  let dialogRefMock: any;

  beforeEach(async () => {
    dialogRefMock = {
      close: jasmine.createSpy('close')
    };

    await TestBed.configureTestingModule({
      imports: [CareLogModalComponent, ReactiveFormsModule, NoopAnimationsModule],
      providers: [
        { provide: MatDialogRef, useValue: dialogRefMock },
        { provide: MAT_DIALOG_DATA, useValue: { plantId: 1 } }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CareLogModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should close dialog with form value on submit', () => {
    component.form.patchValue({
      careType: CareType.WATERING,
      logDate: '2024-05-01',
      notes: 'Watered deeply'
    });
    component.onSubmit();
    expect(dialogRefMock.close).toHaveBeenCalledWith({
      plantId: 1,
      careType: CareType.WATERING,
      logDate: '2024-05-01',
      notes: 'Watered deeply'
    });
  });
});
