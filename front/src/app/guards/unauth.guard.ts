import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { SessionService } from "../services/session.service";
import { AuthService } from "../features/auth/services/auth.service";
import { Observable, of } from "rxjs";
import { map, catchError } from "rxjs/operators";
import { User } from "../interfaces/user.interface";

@Injectable({ providedIn: "root" })
export class UnauthGuard implements CanActivate {
  constructor(
    private router: Router,
    private sessionService: SessionService,
    private authService: AuthService
  ) {}

  public canActivate(): Observable<boolean> {
    return this.checkAuthentication();
  }

  private checkAuthentication(): Observable<boolean> {
    if (this.sessionService.user) {
      this.router.navigate(["posts"]);
      return of(false);
    }
    if (this.sessionService.user === null) {
      return of(true);
    }
    return this.authService.me().pipe(
      map((user: User) => {
        this.sessionService.logIn(user);
        this.router.navigate(["posts"]);
        return false;
      }),
      catchError(() => {
        this.sessionService.logOut();
        return [true];
      })
    );
  }
}
