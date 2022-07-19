import {Component, ViewEncapsulation} from "@angular/core";
import {MatDialog} from "@angular/material/dialog";
import {CreateFieldComponent} from "../create_field/create_field.component";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";

export interface DialogData {
  label: string,
  type: string,
  required: boolean,
  active: boolean
}
type Field = {
  label : string,
  type : string,
  required : boolean,
  active : boolean
}

@Component({
  templateUrl: 'main.component.html',
  encapsulation: ViewEncapsulation.None
})

export class MainComponent {
  fields: Field[] = []
  errorMsg = ''

  constructor(private dialog: MatDialog, private http: HttpClient) {
    this.getAllFields();
  }

  openDialog() {
    let newField : Field = {label : '', type : 'Single line text', active : false, required : false};
    const dialogRef = this.dialog.open(CreateFieldComponent, {
      width: '350px', height: '550px',
      data: newField
    });
    dialogRef.afterClosed().subscribe((result : any) => {
      console.log(newField)
      if(result == true){
        this.http.post(environment.apiUrl + "/field", {
          label : newField.label,
          type : newField.type,
          required : newField.required,
          active : newField.active
        }, {withCredentials: true})
          .toPromise()
          .then((result : any) => {
            this.getAllFields();
          })
          .catch((response: any) => {
            if (response.error.details != null) {
              this.errorMsg = response.error.details[0];
            }
            else {
              this.errorMsg = response.error.message;
            }
          })
      }
    })
  }
  getAllFields(){
    this.http.get(environment.apiUrl + "/field", {withCredentials: true})
      .toPromise()
      .then((result : any) =>{
        this.fields = result;
      })
  }
}
