package org.bienkowski.psi.controllers;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bienkowski.psi.dto.CustomUserDetails;
import org.bienkowski.psi.dto.TaskDTO;
import org.bienkowski.psi.model.Task;
import org.bienkowski.psi.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@PreAuthorize("hasRole('USER')")
@Slf4j
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping(value = "/tasks")
    public ResponseEntity<Object> getTasks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String currentPrincipalName = authentication.getName();
            System.out.println(currentPrincipalName + "AUTH SERVIVE");
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            System.out.println(userDetails  + "AUTH SERVIVE");

            UserDetails userDetailsA = (UserDetails) authentication.getPrincipal();
            System.out.println(userDetailsA + "AUTH SERVIVE");
        } else {
            System.out.println("auth null AUTH SERVICE");
        }
        List<TaskDTO> taskList = taskService.getAllTasks();
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    @PostMapping(value = "/tasks/add", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public TaskDTO addTask( @Valid  @RequestBody TaskDTO taskDTO ) {
        Optional<TaskDTO> savedTask = taskService.saveTask(taskDTO, 1);
        if (savedTask.isPresent()) {
            return savedTask.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/tasks/{idTsk}")
    public ResponseEntity<Object> deleteTask(@PathVariable Integer idTsk) {
        taskService.deleteTask(idTsk);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
