import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogData} from "../main/main.component";

@Component({
  templateUrl: 'create_field.component.html'
})
export class CreateFieldComponent{
  typeChoices = [
    "Single line text",
    "Multiline text",
    "Radio button",
    "Checkbox",
    "Combobox",
    "Date"
  ]
  deletedType : any;
  constructor(public dialogRef: MatDialogRef<CreateFieldComponent>,@Inject(MAT_DIALOG_DATA) public data: DialogData) {
    this.typeChoices.forEach((element, index)=>{
      if(element == data.type) this.deletedType = this.typeChoices.splice(index,1);
    })
  }

  back(){
    this.dialogRef.close();
  }

  changeType(e : any){
    this.data.type = e.target.value;
  }

  changeRequired(){
    this.data.required = !this.data.required;
  }
  changeIsActive(){
    this.data.active = !this.data.active;
  }
  returnTrue(){
    return true;
  }
}
