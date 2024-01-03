import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { User } from "../interfaces/user.interface";

@Injectable({
  providedIn: "root",
})
export class SessionService {
  public isLogged: boolean = false;
  public user: User | undefined | null;

  private isLoggedSubject: BehaviorSubject<boolean> =
    new BehaviorSubject<boolean>(this.isLogged);

  get isLogged$(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(user: User): void {
    this.isLogged = true;
    this.user = user;
    this.next();
  }

  public logOut(): void {
    localStorage.removeItem("token");
    this.isLogged = false;
    this.user = null;
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}
