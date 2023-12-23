import { Component, OnInit } from "@angular/core";

import { Post } from "../../interfaces/post.interface";
import { take, tap } from "rxjs";
import { PostsResponse } from "../../interfaces/postsResponse.interface";
import { PostService } from "../../services/post.service";
import { ActivatedRoute } from "@angular/router";
import { Message } from "../../interfaces/message.interface";
import { MessageService } from "../../services/message.service";
import { FormBuilder, Validators } from "@angular/forms";
import { MessageRequest } from "../../interfaces/messageRequest.interface";
import { MessageApiResponse } from "src/app/interfaces/messageApiResponse.interface";
import { MessagesResponse } from "../../interfaces/MessagesResponse.interface copy";

@Component({
  selector: "app-detail",
  templateUrl: "./detail.component.html",
  styleUrls: ["./detail.component.scss"],
})
export class DetailComponent implements OnInit {
  post!: Post;
  messages!: Message[];
  id: number = +this.route.snapshot.paramMap.get("id")!;

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private messageService: MessageService,
    private fb: FormBuilder
  ) {}

  form = this.fb.group({
    message: [""],
  });

  ngOnInit(): void {
    this.getPostById();
    this.getMessagesByPost();
  }

  getPostById(): void {
    this.postService
      .getPostById(this.id)
      .pipe(
        take(1),
        tap((post: Post) => {
          this.post = post;
        })
      )
      .subscribe();
  }

  getMessagesByPost(): void {
    this.messageService
      .getMessagesByPost(this.id)
      .pipe(
        take(1),
        tap((messagesResponse: MessagesResponse) => {
          this.messages = messagesResponse.messages;
        })
      )
      .subscribe();
  }

  submit(): void {
    let messageRequest = this.form.value as MessageRequest;
    if (messageRequest.message.replace(/\s/g, "").length == 0) {
      return;
    }
    messageRequest.postId = this.id;

    this.messageService.sendMessage(messageRequest).subscribe({
      next: (response: MessageApiResponse) => {
        this.form.reset();
        this.getMessagesByPost();
      },
      error: (error) => {},
    });
  }
}
