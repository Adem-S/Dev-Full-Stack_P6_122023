import { Component, OnInit } from "@angular/core";
import { Router, NavigationEnd } from "@angular/router";
import { Observable } from "rxjs";
import { SessionService } from "./services/session.service";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
})
export class AppComponent implements OnInit {
  showHeader: boolean = false;
  isLogged$!: Observable<boolean>;

  constructor(private router: Router, private sessionService: SessionService) {}

  public ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.showHeader = event.url !== "/";
      }
    });
    this.isLogged$ = this.sessionService.isLogged$;
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate([""]);
  }
}
