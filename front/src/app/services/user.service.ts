import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AuthSuccess } from "../features/auth/interfaces/authSuccess.interface";
import { UpdateUserRequest } from "../interfaces/updateUserRequest.interface";

@Injectable({
  providedIn: "root",
})
export class UserService {
  private pathService: string = "api/user";

  constructor(private httpClient: HttpClient) {}

  public updateUser(
    updateUserRequest: UpdateUserRequest
  ): Observable<AuthSuccess> {
    return this.httpClient.put<AuthSuccess>(
      this.pathService,
      updateUserRequest
    );
  }
}
