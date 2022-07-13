import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogData, LoginComponent} from "../login/login.component";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Component({
  templateUrl: 'forgot_pass.component.html'
})
export class ForgotPassComponent{
  errorMsg: any;
  constructor(public dialogRef: MatDialogRef<ForgotPassComponent>,@Inject(MAT_DIALOG_DATA) public data: DialogData
    ,private http: HttpClient) {}
  back(){
    this.dialogRef.close();
  }
}
