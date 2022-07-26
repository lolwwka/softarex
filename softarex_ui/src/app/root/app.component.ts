import {Component} from '@angular/core';
import {AppService} from "./app.service";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'softarexUi';
  credential: any;

  constructor(private app: AppService, private router: Router, private cookie: CookieService, private http: HttpClient) {
    let cookieInfo = cookie.get('remember-me');
    if (cookieInfo) {
      let emailPass = JSON.parse(cookie.get('remember-me'));
      let email = emailPass.split('/')[0];
      let pass = emailPass.split('/')[1];
      this.credential = {email: email, password: pass}
    }
    this.app.authenticate(this.credential, () => {
      this.router.navigateByUrl('');
    }, () => {
      this.router.navigateByUrl('/login');
    });
  }

  authenticated() {
    return this.app.authenticated;
  }

  userName(){
    return this.app.userName;
  }
  logOut() {
    this.app.authenticated = false;
    this.cookie.delete('remember-me');
    return this.http.post(environment.apiUrl + '/logout', {}, {withCredentials: true}).subscribe(() => {
      this.router.navigateByUrl('/login');
    });
  }
  userId(){
    return this.app.user.id;
  }
  createQuestionnaire(){
    let lastId : any;
    this.http.get(environment.apiUrl + '/questionnaires/lastId').subscribe(data =>{
      lastId = data;
      this.router.navigateByUrl('/questionnaires/' + lastId);
    })
  }
}
