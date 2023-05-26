import { User } from './user.model';

export class Property {
  constructor(
    public uuid: string,
    public name: string,
    public owner: User,
    public address: string,
    public members: User[]
  ) {}
}
