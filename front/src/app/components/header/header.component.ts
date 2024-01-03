import { Component, OnInit, HostListener } from "@angular/core";
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
  screenWidth: number = window.innerWidth;
  currentUrl!: NavigationEnd;

  constructor(private router: Router, private sessionService: SessionService) {}

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.currentUrl = event;
        this.showTheHeader(event);
      }
    });
    this.isLogged$ = this.sessionService.isLogged$;
  }

  showTheHeader(currentUrl: NavigationEnd): void {
    this.showHeader =
      currentUrl.url != "/" &&
      currentUrl.url != "/404" &&
      currentUrl.urlAfterRedirects !== "/" &&
      currentUrl.urlAfterRedirects !== "/404" &&
      (currentUrl.url != "/login" || this.screenWidth > 480) &&
      (currentUrl.url != "/register" || this.screenWidth > 480);
  }

  @HostListener("window:resize", ["$event"])
  onResize(event: Event): void {
    this.screenWidth = (event.target as Window).innerWidth;
    this.showTheHeader(this.currentUrl);
  }

  toggleSideMenu(open: boolean): void {
    this.showSideMenu = open;
    document.body.style.overflow = open ? "hidden" : "auto";
  }
}
