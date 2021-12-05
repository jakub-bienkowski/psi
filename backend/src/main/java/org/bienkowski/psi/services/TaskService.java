package org.bienkowski.psi.services;

import org.bienkowski.psi.dto.CustomUserDetails;
import org.bienkowski.psi.dto.TaskDTO;
import org.bienkowski.psi.dto.UserDTO;
import org.bienkowski.psi.exception.UserAlreadyExistsException;
import org.bienkowski.psi.model.Task;
import org.bienkowski.psi.model.User;
import org.bienkowski.psi.repository.TaskRepository;
import org.bienkowski.psi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Optional<TaskDTO> saveTask(TaskDTO taskDTO, Integer idUsr) {
        Object user =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.toString());
//        CustomUserDetails customUserDetails = (CustomUserDetails) user;
//        System.out.println(customUserDetails.getIdUsr());


//        Task task = modelMapper.map(taskDTO, Task.class);
//        Optional<User> user = userRepository.findById(idUsr);
//        if (user.isPresent()) {
//            List<Task> userTasks = user.get().getTasks();
//            userTasks.add(task);
//            taskDTO.setIdTsk(task.getIdTsk());
//            return Optional.of(taskDTO);
//        }
        return Optional.empty();
    }

    public List<TaskDTO> getAllTasks() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            String currentPrincipalName = authentication.getName();
//            System.out.println(currentPrincipalName + "AUTH SERVIVE");
//            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//            System.out.println(userDetails  + "AUTH SERVIVE");
//
//            UserDetails userDetailsA = (UserDetails) authentication.getPrincipal();
//            System.out.println(userDetailsA + "AUTH SERVIVE");
//        } else {
//            System.out.println("auth null AUTH SERVICE");
//        }

//        Iterable<Task> taskList = taskRepository.findAll();
//        List<TaskDTO> taskDTOs = new ArrayList<>();
//        modelMapper.map(taskList, taskDTOs);
        return new ArrayList<TaskDTO>();
//        return taskDTOs;
    }

    public void deleteTask(Integer idTsk) {
        Task task = new Task();
        task.setIdTsk(idTsk);
        taskRepository.delete(task);
    }
}
