import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Patient } from '../../../models/patient.model';
import { PatientService } from '../../../services/patient.service';

@Component({
  selector: 'app-patient-edit-dialog',
  templateUrl: './patient-edit-dialog.component.html',
  styleUrl: './patient-edit-dialog.component.scss'
})
export class PatientEditDialogComponent {
  patient: Patient;
  isNewPatient: boolean;

  constructor(
    public dialogRef: MatDialogRef<PatientEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Patient,
    private patientService: PatientService
  ) {
    if (data) {
      this.patient = { ...data };
      this.isNewPatient = false;
    } else {
      // Initialize an empty patient object for creation
      this.patient = {
        pid: undefined,
        firstName: '',
        lastName: '',
        dateOfBirth: null,
        gender: '',
        address: '',
        suburb: '',
        state: '',
        postcode: '',
        phoneNo: ''
      };
      this.isNewPatient = true;
    }
  }

  onSubmit(): void {
    if (this.patient.dateOfBirth) {
      this.patient.dateOfBirth = new Date(this.patient.dateOfBirth);
    }
    if (this.isNewPatient) {
      this.patientService.createPatient(this.patient)
        .subscribe(newPatient => {
          this.dialogRef.close(newPatient);
        });
    } else {
      this.patientService.updatePatient(this.patient.pid!, this.patient)
        .subscribe(updatedPatient => {
          this.dialogRef.close(updatedPatient);
        });
    }
  }

  validatePhoneNumber(event: any): void {
    const inputValue = event.target.value;
    if (isNaN(inputValue)) {
      event.preventDefault();
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
