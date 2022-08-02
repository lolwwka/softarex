import {Component} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {WebSocketAPI} from "../websocket/websocket.api";
import {PageEvent} from "@angular/material/paginator";

@Component({
  templateUrl: 'response_table.component.html'
})
export class ResponseTableComponent {
  userAnswers: Array<any> = []
  fieldLabels: Array<any> = []
  fixedUserAnswers: Array<any> = []
  fields: Array<any> = []
  webSocket: WebSocketAPI;
  length: any;
  pageSize = 10;
  pageSizeOptions: number[] = [5, 10, 25, 100];
  index = 0;

  constructor(private http: HttpClient) {
    this.webSocket = new WebSocketAPI(this);
    this.webSocket._connect();
    this.getQuestionnaireLength();
    this.getServerData(this.index, this.pageSize);
  }

  public getNextServerData(event: PageEvent) {
    this.pageSize = event.pageSize;
    this.index = event.pageIndex
    this.getServerData(this.index, this.pageSize)
  }

  private getServerData(offset: number, limit: number) {
    this.userAnswers = []
    this.http.get(environment.apiUrl + '/questionnaires/' + offset + "/" + limit, {withCredentials: true})
      .subscribe((data: any) => {
        data.forEach((userData: any) => {
          this.userAnswers.push(userData.userCashAnswer)
        })
      })
    this.http.get(environment.apiUrl + '/field/totalNum', {withCredentials: true}).subscribe((data: any) => {
      this.http.get(environment.apiUrl + '/field/0/' + data, {withCredentials: true}).subscribe((x: any) => {
        this.fields = x;
      })
    })
  }

  updateUserAnswers(userResp: any) {
    this.userAnswers.push(userResp.userCashAnswer);
  }

  getQuestionnaireLength() {
    this.http.get(environment.apiUrl + '/questionnaires/totalNum', {withCredentials: true}).subscribe(
      data => this.length = data)
  }
}
