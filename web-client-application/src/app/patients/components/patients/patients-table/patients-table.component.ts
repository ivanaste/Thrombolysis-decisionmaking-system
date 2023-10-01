import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {debounceTime, distinctUntilChanged, Observable, Subject, Subscription, take, takeUntil} from "rxjs";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {Store} from "@ngrx/store";
import {User} from "../../../model/User";
import {PatientsService} from "../../../service/patients.service";
import {Router} from "@angular/router";
import {selectRole} from "../../../../auth/store/auth.selectors";

@Component({
  selector: 'app-patients-table',
  templateUrl: './patients-table.component.html',
  styleUrls: ['./patients-table.component.scss']
})
export class PatientsTableComponent implements OnInit, AfterViewInit {
  patients$: Observable<User[]>;
  dataSource: MatTableDataSource<User>;
  displayedColumns: string[];
  search = "";
  private searchSubject: Subject<string> = new Subject<string>();
  private destroy$: Subject<void> = new Subject<void>();
  storeSubscription!: Subscription;
  userRole: string | null = null;


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private store: Store, private patientsService: PatientsService, private router: Router) {
    this.dataSource = new MatTableDataSource<User>();
  }

  ngOnInit(): void {
    this.patients$ = this.patientsService.getAll();
    this.patients$.pipe(take(1)).subscribe(patients => {
      this.dataSource.data = patients;
    })
    this.storeSubscription = this.store
      .select(selectRole)
      .subscribe((role) => (this.userRole = role));
    this.displayedColumns = this.userRole === 'NURSE' ? ['Jmbg Pacijenta', 'Datum rodjenja', 'Ime', 'Prezime', 'Brisanje'] : ['Jmbg Pacijenta', 'Datum rodjenja', 'Ime', 'Prezime', 'Procena rizika', 'Primena terapije', 'Brisanje'];
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.searchSubject.pipe(
      debounceTime(300), // Debounce for 300 milliseconds
      distinctUntilChanged(),
      takeUntil(this.destroy$)
    ).subscribe(() => {
      this.patients$.subscribe(patient => {
        this.dataSource.data = patient.filter(patient => patient?.jmbg.includes(this.search) || patient?.name.includes(this.search) || patient?.surname.includes(this.search) || patient?.email.includes(this.search));
      });
    });
  }

  onSearchInput(): void {
    this.searchSubject.next(this.search);
  }

  editPatient(row: any) {
    this.router.navigate(['/patients/edit/', row.email]);
  }

  makeDecision(patient: any) {
    this.router.navigate(['/decisions/trenutakNastanka/', patient.jmbg]);
  }

  makeRistEstimation(patient: any) {
    this.router.navigate(['/risk-estimations/procenaRizikaOdMU/', patient.jmbg, patient.datumRodjenja]);
  }

  onDelete(id: string) {
    this.patientsService.delete(id).subscribe((patients) => {
      this.dataSource.data = patients;
    })
  }

}
