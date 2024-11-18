import { Role } from '../../auth/models/role';

export class UserRegistrationDTO {
  constructor(
    firstName: string,
    lastName: string,
    userName: string,
    password: string,
    email: string,
    typeUser: Role
  ) {}
}
