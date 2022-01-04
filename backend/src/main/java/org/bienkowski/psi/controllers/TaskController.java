package org.bienkowski.psi.controllers;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bienkowski.psi.dto.CustomUserDetails;
import org.bienkowski.psi.dto.TaskDTO;
import org.bienkowski.psi.model.Task;
import org.bienkowski.psi.services.TaskService;
import org.bienkowski.psi.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping()
    public List<TaskDTO> getTasks() {
       Optional<CustomUserDetails> currentUser = UserUtils.getCurrentUser();
        if (currentUser.isPresent()) {
            return taskService.getUserTasks(currentUser.get()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/add", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public TaskDTO addTask(@RequestBody TaskDTO taskDTO) {
        Optional<CustomUserDetails> currentUser = UserUtils.getCurrentUser();
        if (currentUser.isPresent()) {
            Optional<TaskDTO> savedTask = taskService.saveTask(taskDTO, currentUser.get());
            return savedTask.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{idTsk}")
    public ResponseEntity<Object> deleteTask(@PathVariable Integer idTsk) {
        Optional<CustomUserDetails> currentUser = UserUtils.getCurrentUser();
        if (currentUser.isPresent()) {
            taskService.deleteTask(idTsk);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
