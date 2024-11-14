import { Component, OnInit, ViewChild  } from '@angular/core';
import { PatientService } from '../../../services/patient.service';
import { Patient } from '../../../models/patient.model';
import { PageEvent } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { PatientEditDialogComponent } from '../patient-edit-dialog/patient-edit-dialog.component';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.scss']
})
export class PatientListComponent implements OnInit {
  patients: Patient[] = [];
  filteredPatients: Patient[] = [];
  totalPatients: number = 0;
  pageSize: number = 10;
  pageIndex: number = 0;
  search: string = '';
  columnSort: string = 'ID';
  sortDirection: string = 'ASC';
  displayedColumns: string[] = ['pid', 'firstName', 'lastName', 'dateOfBirth', 'gender', 'address', 'suburb', 'state', 'postcode', 'phoneNo', 'actions'];
  loading = false;

  @ViewChild(MatSort) sort: MatSort = {} as MatSort;
  dataSource: MatTableDataSource<Patient> = new MatTableDataSource();

  constructor(
    private patientService: PatientService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.loadPatients();
    this.loading = false;
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
  }

  loadPatients(): void {
    this.loading = true;
    this.patientService.getPatients(this.search, this.pageIndex, this.pageSize, this.columnSort, this.sortDirection)
      .subscribe(data => {
        this.patients = data.content;      
        this.filteredPatients = this.patients;
        this.totalPatients = data.totalElements;  
        this.dataSource.data = this.patients;
        this.loading = false;
      });
  }

  onSearchChange(): void {
    const filterValue = this.search.trim().toLowerCase();
    this.dataSource.filter = filterValue;
  }

  deletePatient(id: number): void {
    this.patientService.deletePatient(id).subscribe(() => {
      this.loadPatients();
    });
  }

  openCreateDialog(): void {
    this.loading = true;
    const dialogRef = this.dialog.open(PatientEditDialogComponent, {
      width: '400px',
      data: null
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadPatients();
      }
    });
    this.loading = false;
  }

  editPatient(id: number): void {
    this.loading = true;
    this.patientService.getPatientById(id).subscribe(patient => {
      const dialogRef = this.dialog.open(PatientEditDialogComponent, {
        width: '500px',  
        data: patient  
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.loadPatients();
        }
      });
      this.loading = false;
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadPatients();
  }

}
