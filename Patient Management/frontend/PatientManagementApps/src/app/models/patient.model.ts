export interface Patient {
    pid?: number;
    firstName: string;
    lastName: string;
    dateOfBirth: Date | null;
    gender: string;
    address: string;
    suburb: string;
    state: string;
    postcode: string;
    phoneNo: string;
  }
  