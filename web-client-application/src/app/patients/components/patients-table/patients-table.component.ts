import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {debounceTime, distinctUntilChanged, Observable, Subject, take, takeUntil} from "rxjs";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {Store} from "@ngrx/store";
import {Patient} from "../../model/Patient";
import {PatientsService} from "../../service/patients.service";

@Component({
  selector: 'app-patients-table',
  templateUrl: './patients-table.component.html',
  styleUrls: ['./patients-table.component.scss']
})
export class PatientsTableComponent implements OnInit, AfterViewInit {
  patients$: Observable<Patient[]>;
  dataSource: MatTableDataSource<Patient>;
  displayedColumns: string[] = ['Jmbg Pacijenta', 'Datum rodjenja', 'Ime', 'Prezime'];
  search = "";
  private searchSubject: Subject<string> = new Subject<string>();
  private destroy$: Subject<void> = new Subject<void>();


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private store: Store, private patientsService: PatientsService) {
    this.dataSource = new MatTableDataSource<Patient>();
  }

  ngOnInit(): void {
    this.patients$ = this.patientsService.getAll();
    this.patients$.pipe(take(1)).subscribe(patients => {
      this.dataSource.data = patients;
    })
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

}
