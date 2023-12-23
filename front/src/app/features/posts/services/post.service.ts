import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { MessageApiResponse } from "src/app/interfaces/messageApiResponse.interface";
import { PostsResponse } from "../interfaces/postsResponse.interface";
import { Post } from "../interfaces/post.interface";
import { PostRequest } from "../interfaces/postRequest.interface";

@Injectable({
  providedIn: "root",
})
export class PostService {
  private pathService = "api/post";

  constructor(private httpClient: HttpClient) {}

  public getFeedPosts(): Observable<PostsResponse> {
    return this.httpClient.get<PostsResponse>(this.pathService);
  }

  public getPostById(id: number): Observable<Post> {
    return this.httpClient.get<Post>(`${this.pathService}/${id}`);
  }

  public addPost(postRequest: PostRequest): Observable<MessageApiResponse> {
    return this.httpClient.post<MessageApiResponse>(
      this.pathService,
      postRequest
    );
  }
}
