import {Component} from "@angular/core";
import {AppService} from "../root/app.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {environment} from "../../environments/environment";

@Component({
  templateUrl: 'change_pass.component.html'
})
export class ChangePassComponent{
  errorMsg : any
  passInfo = {
    currentPass : '',
    newPass : '',
    newPassConfirm : ''
  }
  passTypes = {
    currentPass : 'password',
    newPass : 'password',
    newPassConfirm : 'password'
  }

  constructor(private app : AppService, private http: HttpClient, private router: Router) {
  }

  updatePass(){
    if(this.passInfo.newPass != this.passInfo.newPassConfirm){
      this.errorMsg = "Passwords not equals"
      return
    }
    this.http.put(environment.apiUrl + "/user/" + this.app.user.id + "/changePass", {
      currentPass: this.passInfo.currentPass,
      newPass: this.passInfo.newPass
    }, {withCredentials: true})
      .toPromise()
      .then( () =>{
        this.router.navigateByUrl('/');
      })
      .catch((response : any) =>{
        if(response.error.details != null) {
          this.errorMsg = response.error.details[0];
        }
        else this.errorMsg = response.error.message;
      })
  }
  changeCurrentPass(){
    if(this.passTypes.currentPass == 'password'){
      this.passTypes.currentPass = 'text';
      this.passTypes.newPass = 'text';
      this.passTypes.newPassConfirm = 'text';
    }else{
      this.passTypes.currentPass = 'password';
      this.passTypes.newPass = 'password';
      this.passTypes.newPassConfirm = 'password';    }
  }
}
