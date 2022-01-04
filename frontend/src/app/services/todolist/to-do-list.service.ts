import { map } from 'rxjs/operators';
import { Task } from './../../shared/models/task';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {  Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ToDoListService {

  constructor(private http: HttpClient) {
  }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  getTasks(): Observable<Array<Task>> {
    return this.http.get<Array<Task>>(environment.api.serverhost + environment.api.tasks);
  }

  saveTask(task: Task) {
    return this.http
      .post<Task>(environment.api.serverhost + environment.api.tasks + '/add', task, this.httpOptions)
      .pipe(
         map((savedTask: Task) => {
         return savedTask;
      })
    );
  }

  removeTask(idTsk: number) {
    return this.http.delete(environment.api.serverhost + environment.api.tasks + '/' + idTsk, this.httpOptions);
  }

}
