import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {ResponseTableComponent} from "../response_table/response_table.component";

export class WebSocketAPI {
  webSocketEndPoint: string = 'http://localhost:8080/softarex/ws';
  topic: string = "/questionnaire/new";
  stompClient: any;
  responseTableComponent : ResponseTableComponent;

  constructor(responseTableComponent : ResponseTableComponent) {
    this.responseTableComponent = responseTableComponent;
  }

  _connect() {
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    const _this = this;
    _this.stompClient.connect({}, function (frame : any) {
      _this.stompClient.subscribe(_this.topic, function (sdkEvent : any) {
        _this.onMessageReceived(sdkEvent);
      });
      // _this.stompClient.reconnect_delay = 2000;
    }, this.errorCallBack);
  };

  _disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
  }

  errorCallBack(error : any) {
    console.log("errorCallBack -> " + error)
    setTimeout(() => {
      this._connect();
    }, 5000);
  }

  /**
   * Send message to sever via web socket
   * @param {*} message
   */
  _send(message : any) {
    this.stompClient.send("/app/questionnaire/create", {}, JSON.stringify(message));
  }

  onMessageReceived(message : any) {
    this.responseTableComponent.updateUserAnswers(message)
  }
}
