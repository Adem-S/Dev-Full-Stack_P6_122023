import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Subject } from "../interfaces/subject.interface";
import { MessageApiResponse } from "src/app/interfaces/messageApiResponse.interface";
import { SubjectsResponse } from "../interfaces/subjectsResponse.interface";

@Injectable({
  providedIn: "root",
})
export class SubjectService {
  private pathService = "api/subject";

  constructor(private httpClient: HttpClient) {}

  public getSubjects(): Observable<SubjectsResponse> {
    return this.httpClient.get<SubjectsResponse>(this.pathService);
  }

  public getSubjectSubscribed(): Observable<SubjectsResponse> {
    return this.httpClient.get<SubjectsResponse>(`${this.pathService}/user`);
  }

  public subscribeFromSubject(id: number): Observable<MessageApiResponse> {
    return this.httpClient.post<MessageApiResponse>(
      `${this.pathService}/subscribe/${id}`,
      {}
    );
  }

  public unsubscribeFromSubject(id: number): Observable<MessageApiResponse> {
    return this.httpClient.delete<MessageApiResponse>(
      `${this.pathService}/unsubscribe/${id}`
    );
  }
}
