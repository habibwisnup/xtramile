import { Injectable } from '@angular/core';
import { HttpClient, HttpParams  } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Patient } from '../models/patient.model';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  private baseUrl = 'http://localhost:8080/api/patients';

  constructor(private http: HttpClient) { }

  getPatients(search: string = '', page: number = 0, size: number = 10, columnSort: string = 'ID', sortDirection: string = 'ASC'): Observable<any> {
    let params = new HttpParams()
      .set('search', search)
      .set('page', page.toString())
      .set('size', size.toString())
      .set('columnSort', columnSort)
      .set('sortDirection', sortDirection);
    
    return this.http.get<any>(`${this.baseUrl}/all`, { params });
  }

  getPatientById(id: number): Observable<Patient> {
    const params = new HttpParams().set('pid', id.toString());
    return this.http.get<Patient>(`${this.baseUrl}`, { params });
  }

  createPatient(patient: Patient): Observable<Patient> {
    return this.http.post<Patient>(this.baseUrl, patient);
  }

  updatePatient(id: number, patient: Patient): Observable<Patient> {
    return this.http.put<Patient>(`${this.baseUrl}/${id}`, patient);
  }

  deletePatient(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
