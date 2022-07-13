import {Component} from "@angular/core";
import {AppService} from "../root/app.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";
import {MatDialog} from "@angular/material/dialog";
import {ForgotPassComponent} from "../forgot_pass/forgot_pass.component";
import {environment} from "../../environments/environment";

export interface DialogData{
  width : any;
  height: any;
  email : '';
  error : any
}

@Component({
  templateUrl: 'login.component.html'
})

export class LoginComponent{
  credentials = {email: '', password: ''}
  errorMsg :any;
  checkBox = {name: 'Remember me', check: false, color: 'primary'}
  cookie : CookieService;
  checked = false;
  email = '';

  constructor(private app: AppService, private http: HttpClient, private router: Router, cookie: CookieService, private dialog: MatDialog) {
    this.cookie = cookie;
  }
  login(){
    if(this.checked){
      let expiredDate = new Date()
      expiredDate.setDate(expiredDate.getDate() + 14);
      this.cookie.set('remember-me', JSON.stringify(this.credentials.email + '/' + this.credentials.password), expiredDate);
    }
      this.app.authenticate(this.credentials, () =>{
        this.errorMsg = null;
        this.router.navigateByUrl('/');
      }, ()=> {
        this.errorMsg = 'Bad password or email';
      });
      return false;
  }
  change(){
    this.checked = !this.checked;
  }
  openDialog() : void {
    const dialogRef = this.dialog.open(ForgotPassComponent,{width : '350px', height: '450px',data: {email : this.email}});
    dialogRef.afterClosed().subscribe(result =>{
      this.email = result;
      this.http.post(environment.apiUrl + "/user/passRecovery/" + this.email,{})
        .toPromise()
        .then( ()=>{
          this.errorMsg = "Mail have been send";
        })
        .catch((response : any) =>{
          if(response.error.details != null) {
            this.errorMsg = response.error.details[0];
          }
          else this.errorMsg = response.error.message;
        })
    })
  }

}
