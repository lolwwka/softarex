import {Component} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {WebSocketAPI} from "../websocket/websocket.api";

@Component({
  templateUrl : 'response_table.component.html'
})
export class ResponseTableComponent{
  userAnswers : Array<any> = []
  fieldLabels : Array<any> = []
  fixedUserAnswers : Array<any> = []
  fields : Array<any> = []
  webSocket : WebSocketAPI;

  constructor(private http: HttpClient) {
    this.webSocket = new WebSocketAPI(this);
    this.webSocket._connect();
    this.http.get(environment.apiUrl + '/questionnaires', {withCredentials:true}).subscribe( (data : any) =>{
      data.forEach( (arrData : any) =>{
        this.userAnswers.push(arrData.userAnswers);
        this.fieldLabels.push(arrData.fieldLabels);
      })
      this.userAnswers.forEach( userAnswer =>{
        this.fixedUserAnswers.push(userAnswer.map((item : any) => item === undefined || item == "" ? 'N/A' : item))
        if(userAnswer.length < this.fields.length){
          for (let i = 0; i < userAnswer.length - this.fields.length; i++) {
            this.fixedUserAnswers.push('N/A')
          }
        }
      })
    })
    this.http.get(environment.apiUrl + '/field', {withCredentials:true}).subscribe( (data : any) =>{
      this.fields = data;
    })
  }
  updateUserAnswers(userResp : any){
    userResp.forEach( (item : any) =>{
      this.fixedUserAnswers.push(item.map( (x : any) => x === undefined || x == "" ? 'N/A' : item))
    })
  }
}
