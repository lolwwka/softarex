import {Component} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {environment} from "../../environments/environment";
import {Validators} from "@angular/forms";

@Component({
  templateUrl: 'register.component.html'
})
export class RegisterComponent {
  credentials = {email: '', password: '', confirmPassword: '', firstName: '', lastName: '', phoneNumber: ''}
  error : any;

  constructor(private http: HttpClient, private router: Router) {
  }

  register() {
    this.error = null;
    this.doValidation();
    if(this.error != null){
      return;
    }
    if(this.credentials.password !== this.credentials.confirmPassword){
      this.error = "Passwords not equal";
      return
    }
    this.http.post(environment.apiUrl + "/register", {
      email: this.credentials.email,
      password: this.credentials.password,
      firstName: this.credentials.firstName,
      lastName: this.credentials.lastName,
      phoneNumber: this.credentials.phoneNumber
    })
      .toPromise()
      .then(() => {
          this.router.navigateByUrl("/login")
      })
      .catch((response : any) =>{
        if(response.error.details != null) {
          this.error = response.error.details[0];
        }
        else this.error = response.error.message;
      })
  }

  doValidation(){
    if(this.credentials.password.length < 5){
      this.error = "Password must be more than 5 symbols"
    }
    if(this.credentials.password.length > 25){
      this.error = "Password must be less than 25 symbols"
    }
    if(this.credentials.firstName.length > 25){
      this.error = "First name must be less than 25 letters"
    }
    if(this.credentials.lastName.length > 25){
      this.error = "Last name must be less 25 letters"
    }
  }
}
