import { TokenstorageService } from './../../../../services/tokenstorage/tokenstorage.service';
import { BehaviorSubject, Observable } from 'rxjs';
import { Task } from './../../../../shared/models/task';
import { Component, OnInit } from '@angular/core';
import { ToDoListService } from 'src/app/services/todolist/to-do-list.service';

@Component({
  selector: 'app-todolist',
  templateUrl: './todolist.component.html',
  styleUrls: ['./todolist.component.scss']
})
export class TodolistComponent implements OnInit {

  taskValue: string = '';
  taskList: Task[] = [];

  constructor(private listService: ToDoListService, private token: TokenstorageService) {
  }

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks() {
    this.listService.getTasks().subscribe(tasks => {
      this.taskList = tasks;
    })
  }

  addTask() {
    let task = this.buildTask();
    this.listService.saveTask(task).subscribe(task => {
      this.taskList.push(task);
    });
  }

  buildTask(): Task {
    return {
      idTsk: null,
      value: this.taskValue
    }
  }

  removeTask(idTsk: number | null) {
    if (idTsk === null) {
      return;
    }
    this.listService.removeTask(idTsk);
    this.taskList.filter(task => {
      task.idTsk !== idTsk;
    })
  }

}
