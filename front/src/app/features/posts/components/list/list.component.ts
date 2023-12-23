import { Component, OnInit } from "@angular/core";

import { Post } from "../../interfaces/post.interface";
import { take, tap } from "rxjs";
import { PostsResponse } from "../../interfaces/postsResponse.interface";
import { PostService } from "../../services/post.service";

@Component({
  selector: "app-list",
  templateUrl: "./list.component.html",
  styleUrls: ["./list.component.scss"],
})
export class ListComponent implements OnInit {
  posts!: Post[];
  sortMode: string = "desc";

  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.getFeedPosts();
  }

  getFeedPosts(): void {
    this.postService
      .getFeedPosts()
      .pipe(
        take(1),
        tap((postsResponse: PostsResponse) => {
          this.posts = postsResponse.posts;
        })
      )
      .subscribe();
  }

  sortPosts(mode: string): void {
    this.sortMode = mode;
    this.posts = this.posts.sort((a, b) => {
      const dateA = new Date(a.date);
      const dateB = new Date(b.date);
      return mode == "desc"
        ? dateB.getTime() - dateA.getTime()
        : dateA.getTime() - dateB.getTime();
    });
  }
}
