import { createAction, props, union } from '@ngrx/store';
import { User } from '../../shared/model/user.model';
import { SortDirection } from '../../shared/model/sort-direction';
import { PageResponse } from '../../shared/model/page-response';

export const getUsers = createAction(
  '[Users] Get Users',
  props<{
    pageSize?: number;
    pageNumber?: number;
    searchContent?: string;
    sortField?: string;
    sortDirection?: SortDirection;
    userRole?: string;
  }>()
);

export const setUsers = createAction(
  '[Users] Set Users',
  props<{ userPage: PageResponse<User> }>()
);

export const createUser = createAction(
  '[Users] Create User',
  props<{ email: string; role: string }>()
);

export const createUserSuccess = createAction('[Users] Create User Success');

export const deleteUser = createAction(
  '[Users] Delete User',
  props<{ id: string }>()
);

export const changeUserRole = createAction(
  '[Users] Change User Role',
  props<{ id: string; role: string }>()
);

export const userChangeSuccess = createAction(
  '[Users] User Change Success',
  props<{ id: string; role: string }>()
);

export const deleteUserSuccess = createAction('[Users] Delete User Success');

export const searchUserEmails = createAction(
  '[Users] Search User Emails',
  props<{ value: string }>()
);

export const setUserEmailsSearchResult = createAction(
  '[Users] Set User Email Search Results',
  props<{ users: User[] }>()
);

const all = union({
  getUsers,
  setUsers,
  createUser,
  createUserSuccess,
  deleteUser,
  changeUserRole,
  userChangeSuccess,
  deleteUserSuccess,
  searchUserEmails,
  setUserEmailsSearchResult,
});

export type UsersActionsUnion = typeof all;
