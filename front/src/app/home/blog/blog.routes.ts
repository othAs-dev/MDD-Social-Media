import {Routes} from "@angular/router";
import {BlogComponent} from "./blog.component";

export default [
  {
    path: '',
    component: BlogComponent
    //loadComponent: import('./blog.component').then(m => m.BlogComponent),
  },
  {
    path: '**',
    pathMatch: "full",
    redirectTo: ''
  }
] as Routes
