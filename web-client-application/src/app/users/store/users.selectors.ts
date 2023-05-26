import { createFeatureSelector, createSelector } from '@ngrx/store';
import { State } from './users.reducer';

export const selectUsersState = createFeatureSelector<State>('users');

export const selectUserPage = createSelector(
  selectUsersState,
  (state) => state.userPage
);

export const selectUsers = createSelector(selectUserPage, (userPage) =>
  userPage ? userPage.items : []
);

export const selectUserById = (userId: string) =>
  createSelector(
    selectUsers,
    (users) => users.filter((user) => user.id === userId)[0]
  );

export const selectUserEmailsSearchResults = createSelector(
  selectUsersState,
  (state) => state.userEmailsSearchResult
);
