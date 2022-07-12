import {Component} from "@angular/core";
import {AppService} from "../root/app.service";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Router} from "@angular/router";

@Component({
  templateUrl: 'edit_profile.component.html'
})

export class EditProfileComponent{
  user : any;
  errorMsg : any;
  constructor(private app : AppService, private http: HttpClient, private router: Router) {
    this.user = app.user;
  }
  update(){
    this.app.user.email = this.user.email;
    this.app.user.firstName = this.user.firstName;
    this.app.user.lastName = this.user.lastName;
    this.app.user.phoneNumber = this.user.phoneNumber;
    this.app.setupUsername(this.user);
    this.http.put(environment.apiUrl + "/user", {
      password: '123',
      email: this.user.email,
      id: this.user.id,
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      phoneNumber: this.user.phoneNumber,
    }, {withCredentials: true})
      .toPromise().then((responce : any) =>{
        if(responce.error == false){
          this.router.navigateByUrl('/')
        }else {
          this.errorMsg = responce.message;
        }
    })
  }
}
