import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {debounceTime, distinctUntilChanged, Observable, Subject, Subscription, take, takeUntil} from "rxjs";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {Store} from "@ngrx/store";
import {WorkersService} from "../../../service/workers.service";
import {Router} from "@angular/router";
import {selectRole} from "../../../../auth/store/auth.selectors";
import {User} from "../../../../patients/model/User";

@Component({
  selector: 'app-workers-table',
  templateUrl: './workers-table.component.html',
  styleUrls: ['./workers-table.component.scss']
})
export class WorkersTableComponent implements OnInit, AfterViewInit {
  workers$: Observable<User[]>;
  dataSource: MatTableDataSource<User>;
  displayedColumns: string[] = ['Email', 'Ime', 'Prezime', 'Uloga', 'Brisanje'];
  search = "";
  private searchSubject: Subject<string> = new Subject<string>();
  private destroy$: Subject<void> = new Subject<void>();
  storeSubscription!: Subscription;
  userRole: string | null = null;


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private store: Store, private workersService: WorkersService, private router: Router) {
    this.dataSource = new MatTableDataSource<User>();
  }

  ngOnInit(): void {
    this.workers$ = this.workersService.getAll();
    this.workers$.pipe(take(1)).subscribe(workers => {
      this.dataSource.data = workers;
    })
    this.storeSubscription = this.store
      .select(selectRole)
      .subscribe((role) => (this.userRole = role));
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.searchSubject.pipe(
      debounceTime(300), // Debounce for 300 milliseconds
      distinctUntilChanged(),
      takeUntil(this.destroy$)
    ).subscribe(() => {
      this.workers$.subscribe(worker => {
        this.dataSource.data = worker.filter(worker => worker?.name.includes(this.search) || worker?.surname.includes(this.search) || worker?.email.includes(this.search));
      });
    });
  }

  onSearchInput(): void {
    this.searchSubject.next(this.search);
  }

  editWorker(row: any) {
    this.router.navigate(['/patients/edit/', row.email]);
  }

  onDelete(id: string) {
    this.workersService.delete(id).subscribe((korisnici) => {
      this.dataSource.data = korisnici;
    })
  }

}
