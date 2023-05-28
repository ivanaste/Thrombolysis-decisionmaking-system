import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Store} from "@ngrx/store";
import {debounceTime, distinctUntilChanged, Observable, Subject, take, takeUntil} from "rxjs";
import {RiskEstimation} from "../../model/risk-estimation";
import {selectRiskEstimations} from "../../store/risk-estimation-selectors";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";


@Component({
  selector: 'app-risk-estimation-table',
  templateUrl: './risk-estimation-table.component.html',
  styleUrls: ['./risk-estimation-table.component.scss']
})
export class RiskEstimationTableComponent implements OnInit, AfterViewInit {
  riskEstimations$: Observable<RiskEstimation[]>;
  dataSource: MatTableDataSource<RiskEstimation>;
  displayedColumns: string[] = ['Jmbg Pacijenta', 'createdAt', 'nivoRizika', 'Birth Date'];
  search = "";
  private searchSubject: Subject<string> = new Subject<string>();
  private destroy$: Subject<void> = new Subject<void>();


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private store: Store) {
    this.dataSource = new MatTableDataSource<RiskEstimation>();
  }

  ngOnInit(): void {
    this.riskEstimations$ = this.store.select(selectRiskEstimations);
    this.riskEstimations$.pipe(take(1)).subscribe(riskEstimations => {
      this.dataSource.data = riskEstimations;
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
      this.riskEstimations$.subscribe(riskEstimations => {
        this.dataSource.data = riskEstimations.filter(riskEstimation => riskEstimation.pacijent?.jmbg.includes(this.search));
      });
    });
  }

  onSearchInput(): void {
    this.searchSubject.next(this.search);
  }
}
