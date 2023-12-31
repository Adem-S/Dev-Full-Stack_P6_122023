import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { SubjectsRoutingModule } from "./subject-routing.module";
import { ListComponent } from "./components/list/list.component";
import { MatCardModule } from "@angular/material/card";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";

import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";

const materialModules = [
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
];

@NgModule({
  declarations: [ListComponent],
  imports: [CommonModule, SubjectsRoutingModule, ...materialModules],
})
export class SubjectModule {}
