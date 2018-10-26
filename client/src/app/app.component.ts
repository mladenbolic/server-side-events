import {ChangeDetectorRef, Component} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  public title = 'my-app';
  public systemInfo = 'aaa';

  constructor(private ref: ChangeDetectorRef){
    this.connect();
  }

  connect(): void {
    let eventSource = new EventSource('http://localhost:8080/system-info');

    eventSource.onmessage = (event: MessageEvent) => {
      let json = JSON.parse(event.data);
      this.systemInfo = event.data;
      console.log('Received event: ', event, this.systemInfo);
      // this.quotes.push(new Quote(json['id'], json['book'], json['content']));
      this.ref.detectChanges();
      // observer.next(this.quotes);
    };

    eventSource.onerror = (error) => {
      // readyState === 0 (closed) means the remote source closed the connection,
      // so we can safely treat it as a normal situation. Another way of detecting the end of the stream
      // is to insert a special element in the stream of events, which the client can identify as the last one.
      if(eventSource.readyState === 0) {
        console.log('The stream has been closed by the server.');
        eventSource.close();
        // observer.complete();
      } else {
        // observer.error('EventSource error: ' + error);
      }
    }
  }
}
