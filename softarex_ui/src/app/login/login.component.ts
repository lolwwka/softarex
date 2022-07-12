import {Component} from "@angular/core";
import {AppService} from "../root/app.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";

@Component({
  templateUrl: 'login.component.html'
})

export class LoginComponent{
  credentials = {email: '', password: ''}
  error = false;
  checkBox = {name: 'Remember me', check: false, color: 'primary'}
  cookie : CookieService;
  checked = false;

  constructor(private app: AppService, private http: HttpClient, private router: Router, cookie: CookieService) {
    this.cookie = cookie;
  }
  login(){
    if(this.checked){
      let expiredDate = new Date()
      expiredDate.setDate(expiredDate.getDate() + 14);
      this.cookie.set('remember-me', JSON.stringify(this.credentials.email + '/' + this.credentials.password), expiredDate);
    }
      this.app.authenticate(this.credentials, () =>{
        this.router.navigateByUrl('/');
      }, ()=> {
        this.error = true;
      });
      return false;
  }
  change(){
    this.checked = !this.checked;
  }
}
