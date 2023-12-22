import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { HomeComponent } from "./components/home/home.component";
import { NotFoundComponent } from "./components/not-found/not-found.component";
import { AuthGuard } from "./guards/auth.guard";
import { UnauthGuard } from "./guards/unauth.guard";
import { MeComponent } from "./components/me/me.component";

const routes: Routes = [
  {
    path: "",
    canActivate: [UnauthGuard],
    component: HomeComponent,
  },
  {
    path: "",
    canActivate: [UnauthGuard],
    loadChildren: () =>
      import("./features/auth/auth.module").then((m) => m.AuthModule),
  },
  {
    path: "me",
    canActivate: [AuthGuard],
    component: MeComponent,
  },
  {
    path: "posts",
    canActivate: [AuthGuard],
    loadChildren: () =>
      import("./features/posts/posts.module").then((m) => m.PostsModule),
  },
  {
    path: "subjects",
    canActivate: [AuthGuard],
    loadChildren: () =>
      import("./features/subjects/subject.module").then((m) => m.SubjectModule),
  },
  { path: "404", component: NotFoundComponent },
  { path: "**", redirectTo: "404" },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
