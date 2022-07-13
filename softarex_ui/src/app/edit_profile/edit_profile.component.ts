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
    this.http.put(environment.apiUrl + "/user/" + this.user.id + "/change", {
      password: '12345',
      email: this.user.email,
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      phoneNumber: this.user.phoneNumber,
    }, {withCredentials: true})
      .toPromise()
      .then(() =>{
        this.app.user.email = this.user.email;
        this.app.user.firstName = this.user.firstName;
        this.app.user.lastName = this.user.lastName;
        this.app.user.phoneNumber = this.user.phoneNumber;
        this.app.setupUsername(this.user);
        this.router.navigateByUrl('/');
    })
      .catch((response : any) =>{
        if(response.error.details != null) {
          this.errorMsg = response.error.details[0];
        }
        else this.errorMsg = response.error.message;
      })
  }
}
