import {Component, ViewEncapsulation} from "@angular/core";
import {MatDialog} from "@angular/material/dialog";
import {CreateFieldComponent} from "../create_field/create_field.component";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {PageEvent} from '@angular/material/paginator';

export interface DialogData {
  label: string,
  type: string,
  required: boolean,
  active: boolean
  options : any
}
type Field = {
  id : number,
  label : string,
  type : string,
  required : boolean,
  active : boolean,
  options : any,
}

@Component({
  templateUrl: 'main.component.html',
  encapsulation: ViewEncapsulation.None
})

export class MainComponent {
  length : any;
  pageSize = 5;
  pageSizeOptions: number[] = [5, 10, 25, 100];
  index = 0;
  fields: Field[] = []
  errorMsg = ''

  constructor(private dialog: MatDialog, private http: HttpClient) {
    this.getAllFields(this.index, this.pageSize);
    this.getFieldsLength();
  }
  getFieldsLength(){
    this.http.get(environment.apiUrl + "/field/totalNum", {withCredentials: true}).subscribe(data => this.length = data)
  }

  public getServerData(event:PageEvent){
    this.pageSize = event.pageSize;
    this.index = event.pageIndex
    this.getAllFields(this.index, this.pageSize)
  }

  getAllFields(offset : number, limit : number){
    this.http.get(environment.apiUrl + "/field/" + offset + "/" + limit, {withCredentials: true})
      .toPromise()
      .then((result : any) =>{
        this.fields = result;
      })
  }

  openDialog(id : any) {
    let newField : Field = {label: '', type: 'Single line text', active: false, required: false, id : 0, options : ''};
    if(id != null) {
      this.http.get(environment.apiUrl + "/field/" + id, {withCredentials: true})
        .subscribe((result : any) =>{
          newField = {label: result.label, type: result.type, active: result.active, required: result.required, id: id, options: result.options}
          this.openUpdateDialog(newField, id)
        })
    }else {
      this.openCreateDialog(newField);
    }
  }

  openUpdateDialog(data : Field, id : any){
    const dialogRef = this.dialog.open(CreateFieldComponent, {
      width: '350px', height: '550px',
      data: data
    });
    dialogRef.afterClosed().subscribe((result : any) => {
      if(result == true){
        this.http.put(environment.apiUrl + "/field/" + id, {
          label : data.label,
          type : data.type,
          required : data.required,
          active : data.active,
          options : data.options
        }, {withCredentials: true})
          .toPromise()
          .then(() => {
            this.getAllFields(this.index, this.pageSize);
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

  openCreateDialog(data : Field){
    const dialogRef = this.dialog.open(CreateFieldComponent, {
      width: '350px', height: '550px',
      data: data
    });
    dialogRef.afterClosed().subscribe((result : any) => {
      if(result == true){
        this.http.post(environment.apiUrl + "/field", {
          label : data.label,
          type : data.type,
          required : data.required,
          active : data.active,
          options : data.options
        }, {withCredentials: true})
          .toPromise()
          .then(() => {
            this.getFieldsLength();
            this.getAllFields(this.index, this.pageSize);
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
  deleteField(id : number){
    this.http.delete(environment.apiUrl + "/field/" + id, {withCredentials: true}).subscribe( () =>{
      this.getAllFields(this.index, this.pageSize);
      this.getFieldsLength();
    })
  }
}
