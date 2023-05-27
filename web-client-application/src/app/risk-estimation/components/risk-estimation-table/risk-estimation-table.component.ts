import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Store} from "@ngrx/store";
import {Observable} from "rxjs";
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
  displayedColumns: string[] = ['Jmbg Pacijenta', 'Date', 'Nivo Rizika', 'Birth Date'];

  constructor(private store: Store) {
    this.dataSource = new MatTableDataSource<RiskEstimation>();
  }

  ngOnInit(): void {
    this.riskEstimations$ = this.store.select(selectRiskEstimations);
    this.riskEstimations$.subscribe(riskEstimations => {
      this.dataSource.data = riskEstimations;
    });
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
}
