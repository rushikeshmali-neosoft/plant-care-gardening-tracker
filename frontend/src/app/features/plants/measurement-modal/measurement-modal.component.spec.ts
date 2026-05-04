import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MeasurementModalComponent } from './measurement-modal.component';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('MeasurementModalComponent', () => {
  let component: MeasurementModalComponent;
  let fixture: ComponentFixture<MeasurementModalComponent>;
  let dialogRefMock: any;

  beforeEach(async () => {
    dialogRefMock = {
      close: jasmine.createSpy('close')
    };

    await TestBed.configureTestingModule({
      imports: [MeasurementModalComponent, ReactiveFormsModule, NoopAnimationsModule],
      providers: [
        { provide: MatDialogRef, useValue: dialogRefMock },
        { provide: MAT_DIALOG_DATA, useValue: { plantId: 1 } }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(MeasurementModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should close dialog with form value on submit', () => {
    component.form.patchValue({
      height: 10,
      width: 5,
      measurementDate: '2024-05-01'
    });
    component.onSubmit();
    expect(dialogRefMock.close).toHaveBeenCalledWith({
      plantId: 1,
      height: 10,
      width: 5,
      measurementDate: '2024-05-01',
      notes: ''
    });
  });
});
