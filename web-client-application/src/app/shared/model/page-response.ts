export interface PageResponse<T> {
  items: T[];
  totalElements: number;
  pageSize: number;
  pageNumber: number;
}
