import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

import { MessagesResponse } from "../interfaces/MessagesResponse.interface copy";
import { MessageRequest } from "../interfaces/messageRequest.interface";
import { MessageApiResponse } from "src/app/interfaces/messageApiResponse.interface";

@Injectable({
  providedIn: "root",
})
export class MessageService {
  private pathService: string = "api/message";

  constructor(private httpClient: HttpClient) {}

  public getMessagesByPost(id: number): Observable<MessagesResponse> {
    return this.httpClient.get<MessagesResponse>(
      `${this.pathService}/post/${id}`
    );
  }

  public sendMessage(
    messageRequest: MessageRequest
  ): Observable<MessageApiResponse> {
    return this.httpClient.post<MessageApiResponse>(
      this.pathService,
      messageRequest
    );
  }
}
