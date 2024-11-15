import { Role } from '../../auth/models/role';

export class JwtAuthenticationResponse {
  token: string;
  email: string;
  role: string;

  constructor(
    token: string,
    email: string,
    role: string
  ) {
    this.token = token;
    this.email = email;
    this.role = role;
  }
}
