import { Component, OnInit } from "@angular/core";
import { Router, NavigationEnd } from "@angular/router";
import { Observable } from "rxjs";
import { SessionService } from "src/app/services/session.service";

@Component({
  selector: "app-header",
  templateUrl: "./header.component.html",
  styleUrls: ["./header.component.scss"],
})
export class HeaderComponent implements OnInit {
  showHeader!: boolean;
  showSideMenu: boolean = false;
  isLogged$!: Observable<boolean>;

  constructor(private router: Router, private sessionService: SessionService) {}

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.showHeader =
          event.url != "/" &&
          event.url != "/404" &&
          event.urlAfterRedirects !== "/" &&
          event.urlAfterRedirects !== "/404";
      }
    });
    this.isLogged$ = this.sessionService.isLogged$;
  }

  toggleSideMenu(open: boolean): void {
    this.showSideMenu = open;
    document.body.style.overflow = open ? "hidden" : "auto";
  }
}
