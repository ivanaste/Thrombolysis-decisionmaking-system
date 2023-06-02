import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Store } from '@ngrx/store';
import { User } from '../../../../shared/model/user.model';
import { selectUserPage } from '../../../store/users.selectors';
import { FormControl, FormGroup } from '@angular/forms';
import { getUsers } from '../../../store/users.actions';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-all-users-table',
  templateUrl: './all-users-table.component.html',
  styleUrls: ['./all-users-table.component.scss'],
})
export class AllUsersTableComponent implements OnInit, OnDestroy {
  displayedColumns = ['email', 'role'];
  dataSource!: MatTableDataSource<User>;
  storeSubscription!: Subscription;
  filterAndSortForm!: FormGroup<{
    searchContent: FormControl;
    userRoleFilter: FormControl;
    byFieldSort: FormControl;
    sortOrder: FormControl;
  }>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  totalElements = 0;
  pageSize = 10;

  constructor(private store: Store) {}

  ngOnInit(): void {
    this.filterAndSortForm = new FormGroup({
      searchContent: new FormControl(null),
      userRoleFilter: new FormControl(null),
      byFieldSort: new FormControl(null),
      sortOrder: new FormControl(null),
    });
    this.storeSubscription = this.store
      .select(selectUserPage)
      .subscribe((userPage) => {
        let users: User[] = [];
        if (userPage) {
          users = userPage.items;
          this.totalElements = userPage.totalElements;
          this.pageSize = userPage.pageSize;
          if (this.paginator) {
            this.paginator.pageIndex = userPage.pageNumber;
          }
        }
        this.dataSource = new MatTableDataSource(users);
      });
  }

  ngOnDestroy(): void {
    this.storeSubscription.unsubscribe();
  }

  preventClose($event: MouseEvent) {
    $event.stopPropagation();
  }

  clearFilters() {
    this.filterAndSortForm.controls.searchContent.setValue(null);
    this.filterAndSortForm.controls.userRoleFilter.setValue(null);
    this.filterAndSortForm.controls.byFieldSort.setValue(null);
    this.filterAndSortForm.controls.sortOrder.setValue(null);
    this.searchUsers();
  }

  searchUsers($event?: PageEvent) {
    const searchContent = this.filterAndSortForm.controls.searchContent.value;
    const userRole = this.filterAndSortForm.controls.userRoleFilter.value;
    const sortField = this.filterAndSortForm.controls.byFieldSort.value;
    const sortOrder = this.filterAndSortForm.controls.sortOrder.value;
    const pageSize = $event ? $event.pageSize : this.pageSize;
    const pageNumber = $event ? $event.pageIndex : 0;
    this.store.dispatch(
      getUsers({
        pageSize,
        pageNumber,
        searchContent,
        userRole,
        sortDirection: sortOrder,
        sortField,
      })
    );
  }
}
