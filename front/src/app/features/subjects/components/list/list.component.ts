import { Component, OnInit } from "@angular/core";
import { SubjectService } from "../../services/subject.service";
import { Subject } from "../../interfaces/subject.interface";
import { take, tap } from "rxjs";
import { SubjectsResponse } from "../../interfaces/subjectsResponse.interface";
import { MessageApiResponse } from "src/app/interfaces/messageApiResponse.interface";

@Component({
  selector: "app-list",
  templateUrl: "./list.component.html",
  styleUrls: ["./list.component.scss"],
})
export class ListComponent implements OnInit {
  subjects!: Subject[];

  constructor(private subjectService: SubjectService) {}

  ngOnInit(): void {
    this.getSubjects();
  }

  getSubjects(): void {
    this.subjectService
      .getSubjects()
      .pipe(
        take(1),
        tap((subjectsResponse: SubjectsResponse) => {
          this.subjects = subjectsResponse.subjects;
        })
      )
      .subscribe();
  }

  subscribe(id: number): void {
    this.subjectService
      .subscribeFromSubject(id)
      .pipe(
        take(1),
        tap((messageApiResponse: MessageApiResponse) => {
          this.updateSubjectStatus(id, true);
        })
      )
      .subscribe();
  }

  unsubscribe(id: number): void {
    this.subjectService
      .unsubscribeFromSubject(id)
      .pipe(
        take(1),
        tap((messageApiResponse: MessageApiResponse) => {
          this.updateSubjectStatus(id, false);
        })
      )
      .subscribe();
  }

  updateSubjectStatus(id: number, value: boolean): void {
    this.subjects = this.subjects.map((subject) => {
      if (subject.id == id) {
        subject.subscribed = value;
      }
      return subject;
    });
  }
}
