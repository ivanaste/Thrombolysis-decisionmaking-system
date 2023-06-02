import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { APP_SERVICE_CONFIG, AppConfig } from '../../app-config/app-config';
import { User } from '../../shared/model/user.model';
import { SortDirection } from '../../shared/model/sort-direction';
import { PageResponse } from '../../shared/model/page-response';

@Injectable({
  providedIn: 'root',
})
export class UsersHttpService {
  GET_USERS = 'user';
  CREATE_USER = 'user/register';
  DELETE_USER = 'user/';
  CHANGE_USER_ROLE = 'user/';

  constructor(
    @Inject(APP_SERVICE_CONFIG) private config: AppConfig,
    private http: HttpClient
  ) {}

  getUsers(
    pageSize?: number,
    pageNumber?: number,
    searchContent?: string,
    sortField?: string,
    sortDirection?: SortDirection,
    userRole?: string
  ) {
    let params = new HttpParams();
    if (pageSize) {
      params = params.append('pageSize', pageSize);
    }
    if (pageNumber) {
      params = params.append('pageNumber', pageNumber);
    }
    if (searchContent) {
      params = params.append('search', searchContent);
    }
    if (sortField) {
      params = params.append('sortField', sortField);
    }
    if (sortDirection) {
      params = params.append('sortDirection', sortDirection);
    }
    if (userRole) {
      params = params.append('userRole', userRole);
    }
    return this.http.get<PageResponse<User>>(
      this.config.apiEndpoint + this.GET_USERS,
      {
        params,
      }
    );
  }

  createUser(email: string, role: string) {
    return this.http.post(this.config.apiEndpoint + this.CREATE_USER, {
      email,
      role,
    });
  }

  deleteUser(id: string) {
    return this.http.delete(this.config.apiEndpoint + this.DELETE_USER + id);
  }

  changeUserRole(id: string, role: string) {
    return this.http.put<User>(
      this.config.apiEndpoint + this.CHANGE_USER_ROLE + id,
      {},
      {
        params: new HttpParams().append('roleName', role),
      }
    );
  }
}
