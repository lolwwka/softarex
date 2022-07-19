import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogData} from "../login/login.component";

@Component({
  templateUrl: 'forgot_pass.component.html'
})
export class ForgotPassComponent{

  constructor(public dialogRef: MatDialogRef<ForgotPassComponent>,@Inject(MAT_DIALOG_DATA) public data: DialogData) {}
  back(){
    this.dialogRef.close();
  }
}
