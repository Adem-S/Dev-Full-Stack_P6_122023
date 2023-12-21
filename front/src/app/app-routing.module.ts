import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { HomeComponent } from "./components/home/home.component";
import { AuthGuard } from "./guards/auth.guard";
import { UnauthGuard } from "./guards/unauth.guard";

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
    path: "posts",
    canActivate: [AuthGuard],
    loadChildren: () =>
      import("./features/posts/posts.module").then((m) => m.PostsModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
