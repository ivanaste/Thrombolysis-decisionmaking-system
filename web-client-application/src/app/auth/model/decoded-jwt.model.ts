export class DecodedJwt {
  constructor(
    public certificateStatus: string,
    public role: string,
    public exp: number,
    public sub: string
  ) {}
}
