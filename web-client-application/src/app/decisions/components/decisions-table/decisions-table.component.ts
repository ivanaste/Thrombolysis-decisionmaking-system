import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Store} from "@ngrx/store";
import {Observable} from "rxjs";
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
  displayedColumns: string[] = ['Jmbg Pacijenta', 'Date', 'Status', 'Birth Date'];

  constructor(private store: Store) {
    this.dataSource = new MatTableDataSource<Decision>();
  }

  ngOnInit(): void {
    this.decisions$ = this.store.select(selectDecisions);
    this.decisions$.subscribe(decisions => {
      this.dataSource.data = decisions;
    });
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
}
