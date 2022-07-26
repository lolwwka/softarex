import {Component} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {ActivatedRoute, Router} from "@angular/router";
import {WebSocketAPI} from "../websocket/websocket.api";
import {ResponseTableComponent} from "../response_table/response_table.component";

@Component({
  templateUrl: 'create_response.component.html'
})
export class CreateResponseComponent {

  serverData : Array<any> = [];
  singleLineTextData : Array<any> = [];
  multilineTextData : Array<any> = [];
  radioButtonData : Array<any> = [];
  checkboxData : Array<any> = [];
  comboboxData : Array<any> = [];
  dateData : Array<any> = [];
  response : Array<any> = [];
  errorMsg : any;
  responseId : any;
  webSocket : WebSocketAPI;

  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router) {
    this.webSocket = new WebSocketAPI(new ResponseTableComponent(this.http));
    this.responseId = this.route.snapshot.paramMap.get('id');
    this.response = [];
    this.webSocket._connect();
    http.get(environment.apiUrl + "/questionnaires/" + this.responseId).toPromise().then( (data : any) =>{
      this.serverData = data;
      this.serverData.forEach( data =>{
        data.answer = '';
        switch (data.type){
          case "Single line text" :
            this.singleLineTextData.push(data);
            break
          case "Multiline text" :
            this.multilineTextData.push(data);
            break
          case "Radio button" :
            if(data.options != '' && data.options != null) {
              data.options = data.options.split('\n');
            }
            this.radioButtonData.push(data);
            break
          case "Checkbox" :
            data.answer = false;
            this.checkboxData.push(data);
            break
          case "Combobox" :
            if(data.options != '' && data.options != null) {
              data.options = data.options.split('\n');
            }
            this.comboboxData.push(data);
            break
          case "Date" :
            data.answer = new Date('2020:10:25');
            this.dateData.push(data);
        }
      })
    })
      .catch((response : any) =>{
        if(response.error.details != null) {
          this.errorMsg = response.error.details[0];
        }
        else this.errorMsg = response.error.message;
      })
  }
  submit(){
    this.singleLineTextData.forEach(data => {
      this.response.push({id : data.id, answer : data.answer, required : data.required});
    })
    this.multilineTextData.forEach(data => {
      this.response.push({id : data.id, answer : data.answer, required : data.required});
    })
    this.radioButtonData.forEach(data => {
      this.response.push({id : data.id, answer : data.answer, required : data.required});
    })
    this.dateData.forEach(data => {
      this.response.push({id : data.id, answer : data.answer, required : data.required});
    })
    this.checkboxData.forEach(data => {
      this.response.push({id : data.id, answer : data.answer, required : data.required});
    })
    this.comboboxData.forEach(data => {
      this.response.push({id : data.id, answer : data.answer, required : data.required});
    })
    this.http.post(environment.apiUrl + '/questionnaires/' + this.responseId, this.response).toPromise().then( ()=>{
      this.webSocket._send(this.response);
      this.response = [];
      this.router.navigateByUrl('/confirm')
    })
      .catch((response : any) =>{
        if(response.error.details != null) {
          this.errorMsg = response.error.details[0];
        }
        else this.errorMsg = response.error.message;
        this.response = [];
      })
  }

  clear(){
    this.singleLineTextData.forEach( data => data.answer = '');
    this.multilineTextData.forEach( data => data.answer = '');
    this.radioButtonData.forEach( data => data.answer = '');
    this.dateData.forEach( data => data.answer = new Date('2020:10:25'));
    this.checkboxData.forEach( data => data.answer = false);
    this.comboboxData.forEach( data => data.answer = '');
  }
}
