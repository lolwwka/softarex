import {Injectable, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from "@angular/common/http";

import {HTTP_INTERCEPTORS, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {RouterModule, Routes} from "@angular/router";

import {AppService} from "./app.service";
import {AppComponent } from './app.component';
import {MainComponent} from "../main/main.component";
import {LoginComponent} from "../login/login.component";
import {RegisterComponent} from "../register/register.component";
import {EditProfileComponent} from "../edit_profile/edit_profile.component";
import {ChangePassComponent} from "../change_pass/change_pass.component";

import {BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from "@angular/material/button";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from "@angular/material/icon";
import {MatListModule} from "@angular/material/list";
import {FormsModule} from "@angular/forms";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {CookieService} from 'ngx-cookie-service';
import {MatMenuModule} from "@angular/material/menu";
import {ForgotPassComponent} from "../forgot_pass/forgot_pass.component";
import {MatDialogModule} from "@angular/material/dialog";
import {CreateFieldComponent} from "../create_field/create_field.component";
import {CreateResponseComponent} from "../create_response/create_response.component";
import {MatRadioModule} from "@angular/material/radio";
import {MatSelectModule} from "@angular/material/select";
import {ConfirmResponseComponent} from "../confirm_response/confirm_response.component";
import {ResponseTableComponent} from "../response_table/response_table.component";
import {WebSocketAPI} from "../websocket/websocket.api";
import {MatPaginatorModule} from "@angular/material/paginator";

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    return next.handle(xhr);
  }
}
const routes: Routes = [
  {path: '', pathMatch: 'full', component: MainComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'user/:id/edit', component: EditProfileComponent},
  {path: 'user/:id/passChange', component: ChangePassComponent},
  {path: 'questionnaires/:id', component: CreateResponseComponent},
  {path: 'confirm', component: ConfirmResponseComponent},
  {path: 'response', component: ResponseTableComponent}
]
@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    LoginComponent,
    RegisterComponent,
    EditProfileComponent,
    ChangePassComponent,
    ForgotPassComponent,
    CreateFieldComponent,
    CreateResponseComponent,
    ConfirmResponseComponent,
    ResponseTableComponent,
  ],
    imports: [
        RouterModule.forRoot(routes),
        BrowserModule,
        HttpClientModule,
        BrowserAnimationsModule,
        MatListModule,
        MatButtonModule,
        MatGridListModule,
        MatInputModule,
        MatIconModule,
        FormsModule,
        MatCheckboxModule,
        MatMenuModule,
        MatDialogModule,
        MatRadioModule,
        MatSelectModule,
        MatPaginatorModule,
    ],
  providers: [AppService, {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true}, CookieService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
