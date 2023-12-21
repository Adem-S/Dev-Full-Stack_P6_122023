import { Component, OnInit } from "@angular/core";
import { FormBuilder, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { SessionService } from "src/app/services/session.service";
import { UserService } from "src/app/services/user.service";
import { UpdateUserRequest } from "../../interfaces/updateUserRequest.interface";
import { AuthSuccess } from "../../features/auth/interfaces/authSuccess.interface";
import { User } from "src/app/interfaces/user.interface";
import { AuthService } from "src/app/features/auth/services/auth.service";

@Component({
  selector: "app-me",
  templateUrl: "./me.component.html",
  styleUrls: ["./me.component.scss"],
})
export class MeComponent implements OnInit {
  onError: boolean = false;
  errorMessage: string = "";
  user!: User | undefined | null;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private sessionService: SessionService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.user = this.sessionService.user;
  }

  public form = this.fb.group({
    username: [
      this.sessionService.user?.username,
      [Validators.required, Validators.min(3)],
    ],
    email: [
      this.sessionService.user?.email,
      [Validators.required, Validators.email],
    ],
  });

  public submit(): void {
    const updateUserRequest = this.form.value as UpdateUserRequest;

    this.userService.updateUser(updateUserRequest).subscribe({
      next: (response: AuthSuccess) => {
        localStorage.setItem("token", response.token);

        this.authService.me().subscribe({
          next: (user: User) => {
            this.sessionService.logIn(user);
          },
          error: () => {
            this.onError = true;
          },
        });
      },
      error: (error) => {
        this.onError = true;
        this.errorMessage = error?.error.message || "Une erreur est survenue";
      },
    });
  }

  logout(): void {
    this.sessionService.logOut();
    this.router.navigate([""]);
  }
}
