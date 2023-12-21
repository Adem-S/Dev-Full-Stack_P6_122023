import { Component } from "@angular/core";
import { FormBuilder, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { User } from "src/app/interfaces/user.interface";
import { SessionService } from "src/app/services/session.service";
import { AuthSuccess } from "../../interfaces/authSuccess.interface";
import { LoginRequest } from "../../interfaces/loginRequest.interface";
import { AuthService } from "../../services/auth.service";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent {
  public hide: boolean = true;
  public onError: boolean = false;
  public errorMessage: string = "";

  public form = this.fb.group({
    emailOrUsername: ["", [Validators.required, Validators.min(3)]],
    password: ["", [Validators.required, Validators.min(8)]],
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private sessionService: SessionService
  ) {}

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;

    this.authService.login(loginRequest).subscribe({
      next: (response: AuthSuccess) => {
        localStorage.setItem("token", response.token);

        this.authService.me().subscribe({
          next: (user: User) => {
            this.sessionService.logIn(user);
            this.router.navigate(["/posts"]);
          },
          error: (error) => {
            this.onError = true;
          },
        });
      },
      error: (error) => {
        this.onError = true;
        this.errorMessage =
          error?.error?.error == "Authentication Error"
            ? "Identifiants ou mot de passe inccorectes"
            : "Une erreur est survenue";
      },
    });
  }
}
