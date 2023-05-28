import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Store} from "@ngrx/store";
import {debounceTime, distinctUntilChanged, Observable, Subject, take, takeUntil} from "rxjs";
import {Decision} from "../../model/decision";
import {selectDecisions} from "../../store/decisions-selectors";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";


@Component({
  selector: 'app-decisions-table',
  templateUrl: './decisions-table.component.html',
  styleUrls: ['./decisions-table.component.scss']
})
export class DecisionsTableComponent implements OnInit, AfterViewInit {
  decisions$: Observable<Decision[]>;
  dataSource: MatTableDataSource<Decision>;
  displayedColumns: string[] = ['pacijent.jmbg', 'createdAt', 'status', 'Datum rodjenja'];
  search = "";
  private searchSubject: Subject<string> = new Subject<string>();
  private destroy$: Subject<void> = new Subject<void>();


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private store: Store) {
    this.dataSource = new MatTableDataSource<Decision>();
  }

  ngOnInit(): void {
    this.decisions$ = this.store.select(selectDecisions);
    this.decisions$.pipe(take(1)).subscribe(decisions => {
      this.dataSource.data = decisions;
      console.log(decisions);
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
      this.decisions$.subscribe(decisions => {
        this.dataSource.data = decisions.filter(decision => decision.pacijent?.jmbg.includes(this.search));
      });
    });
  }

  onSearchInput(): void {
    this.searchSubject.next(this.search);
  }

}
