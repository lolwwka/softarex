import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable()
export class AppService {
  authenticated = false;
  userName = "";
  roles : Array<any> = [];
  callback : any;
  user = {firstName: '', lastName: '', id: 0, email: '', phoneNumber: ''}

  constructor(private http: HttpClient) {
  }

  authenticate(credentials: any, successCallback: any, failureCallback: any) {
    const headers = new HttpHeaders(credentials ? {
      authorization: 'Basic ' + btoa(credentials.email + ':' + credentials.password)
    } : {});
    this.http.post(environment.apiUrl + '/authentication', {}, {headers: headers, withCredentials: true})
      .toPromise()
      .then((response: any) => {
        this.authenticated = true;
        if(response.phoneNumber != null){
          this.user.phoneNumber = response.phoneNumber;
        }
        this.setupUsername(response);
        this.user.id = response.id;
        this.user.email = response.email;
        this.roles = <Array<any>>response.roles;
        return successCallback && successCallback();
      }).catch(() => {
        this.authenticated = false;
        return failureCallback && failureCallback();
      }
    );
  }
  setupUsername(userInfo : any){
    if(userInfo.firstName == null && userInfo.lastName == null || userInfo.firstName == '' && userInfo.lastName == '') {
      this.userName = userInfo.email;
    } else {
      if(userInfo.lastName != null) {
        this.userName = userInfo.firstName + " " + userInfo.lastName;
        this.user.lastName = userInfo.lastName;
        this.user.firstName = userInfo.firstName;
      } else this.userName = userInfo.firstName
    }
  }
}

