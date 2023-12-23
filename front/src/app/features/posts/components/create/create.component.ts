import { Component, OnInit } from "@angular/core";

import { Post } from "../../interfaces/post.interface";
import { take, tap } from "rxjs";
import { PostsResponse } from "../../interfaces/postsResponse.interface";
import { PostService } from "../../services/post.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Message } from "../../interfaces/message.interface";
import { MessageService } from "../../services/message.service";
import { FormBuilder, FormControl, Validators } from "@angular/forms";
import { MessageRequest } from "../../interfaces/messageRequest.interface";
import { MessageApiResponse } from "src/app/interfaces/messageApiResponse.interface";
import { MessagesResponse } from "../../interfaces/MessagesResponse.interface copy";
import { PostRequest } from "../../interfaces/postRequest.interface";
import { SubjectService } from "src/app/features/subjects/services/subject.service";
import { SubjectsResponse } from "src/app/features/subjects/interfaces/subjectsResponse.interface";
import { Subject } from "src/app/features/subjects/interfaces/subject.interface";

@Component({
  selector: "app-create",
  templateUrl: "./create.component.html",
  styleUrls: ["./create.component.scss"],
})
export class CreateComponent implements OnInit {
  onError: boolean = false;
  errorMessage: string = "";
  subjectsSubscribed: Subject[] = [];

  form = this.fb.group({
    subjectId: [0, [Validators.required, Validators.min(1)]],
    title: ["", [Validators.required, Validators.min(1)]],
    content: ["", [Validators.required, Validators.min(1)]],
  });

  constructor(
    private router: Router,
    private postService: PostService,
    private subjectService: SubjectService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.getTitleSubjectSubscribed();
  }

  getTitleSubjectSubscribed(): void {
    this.subjectService
      .getSubjectSubscribed()
      .pipe(
        take(1),
        tap((subjectsResponse: SubjectsResponse) => {
          this.subjectsSubscribed = subjectsResponse.subjects;
        })
      )
      .subscribe();
  }

  submit(): void {
    let postRequest = this.form.value as PostRequest;
    console.log("submit");
    console.log(postRequest);
    this.postService.addPost(postRequest).subscribe({
      next: (response: MessageApiResponse) => {
        this.onError = false;
        this.errorMessage = "";
        this.form.reset();
        this.router.navigate(["/posts"]);
      },
      error: (error) => {
        this.onError = true;
        this.errorMessage = "Une erreur est survenue";
      },
    });
  }
}
