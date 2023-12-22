import { Component, OnInit } from "@angular/core";
import { FormBuilder, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { SessionService } from "src/app/services/session.service";
import { UserService } from "src/app/services/user.service";
import { UpdateUserRequest } from "../../interfaces/updateUserRequest.interface";
import { AuthSuccess } from "../../features/auth/interfaces/authSuccess.interface";
import { User } from "src/app/interfaces/user.interface";
import { AuthService } from "src/app/features/auth/services/auth.service";
import { Subject } from "src/app/features/subjects/interfaces/subject.interface";
import { SubjectService } from "src/app/features/subjects/services/subject.service";
import { SubjectsResponse } from "src/app/features/subjects/interfaces/subjectsResponse.interface";
import { take, tap } from "rxjs";
import { MessageApiResponse } from "src/app/interfaces/messageApiResponse.interface";

@Component({
  selector: "app-me",
  templateUrl: "./me.component.html",
  styleUrls: ["./me.component.scss"],
})
export class MeComponent implements OnInit {
  onError: boolean = false;
  errorMessage: string = "";
  user!: User | undefined | null;
  subjects!: Subject[];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private sessionService: SessionService,
    private userService: UserService,
    private subjectService: SubjectService
  ) {}

  ngOnInit(): void {
    this.user = this.sessionService.user;
    this.subjectService
      .getSubjectSubscribed()
      .pipe(
        take(1),
        tap((subjectsResponse: SubjectsResponse) => {
          this.subjects = subjectsResponse.subjects;
        })
      )
      .subscribe();
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

  unsubscribe(id: number) {
    this.subjectService
      .unsubscribeFromSubject(id)
      .pipe(
        take(1),
        tap((messageApiResponse: MessageApiResponse) => {
          console.log(messageApiResponse);
          this.subjects = this.subjects.filter((subject) => subject.id !== id);
        })
      )
      .subscribe();
  }
}
