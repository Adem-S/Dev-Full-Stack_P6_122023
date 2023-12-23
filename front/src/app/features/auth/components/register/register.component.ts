import { Component } from "@angular/core";
import { FormBuilder, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { SessionService } from "src/app/services/session.service";
import { AuthService } from "../../services/auth.service";
import { RegisterRequest } from "../../interfaces/registerRequest.interface";
import { AuthSuccess } from "../../interfaces/authSuccess.interface";
import { User } from "src/app/interfaces/user.interface";

@Component({
  selector: "app-register",
  templateUrl: "./register.component.html",
  styleUrls: ["./register.component.scss"],
})
export class RegisterComponent {
  public hide: boolean = true;
  public onError: boolean = false;
  public errorMessage: string = "";

  public form = this.fb.group({
    username: ["", [Validators.required, Validators.min(3)]],
    email: ["", [Validators.required, Validators.email]],
    password: ["", [Validators.required, Validators.min(8)]],
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private sessionService: SessionService
  ) {}

  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;

    this.authService.register(registerRequest).subscribe({
      next: (response: AuthSuccess) => {
        localStorage.setItem("token", response.token);
        this.onError = false;
        this.errorMessage = "";
        this.authService.me().subscribe({
          next: (user: User) => {
            this.sessionService.logIn(user);
            this.router.navigate(["/posts"]);
          },
          error: (error) => {
            this.onError = true;
            this.errorMessage = "Une erreur est survenue";
          },
        });
      },
      error: (error) => {
        this.onError = true;
        this.errorMessage = error?.error.message || "Une erreur est survenue";
      },
    });
  }
}
