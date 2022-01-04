package org.bienkowski.psi.services;

import org.bienkowski.psi.dto.CustomUserDetails;
import org.bienkowski.psi.dto.TaskDTO;
import org.bienkowski.psi.model.Task;
import org.bienkowski.psi.model.User;
import org.bienkowski.psi.repository.TaskRepository;
import org.bienkowski.psi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class TaskService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;


    public Optional<List<TaskDTO>> getUserTasks(CustomUserDetails currentUser) {
        Optional<User> user = userRepository.findById(currentUser.getIdUsr());
        if (user.isPresent()) {
            Set<TaskDTO> tasksDto = new HashSet<>();
            Set<Task> tasks = user.get().getTasks();
            modelMapper.map(tasks, tasksDto);
            return Optional.of(tasksDto.stream().toList());
        }
        return Optional.empty();
    }

    public Optional<TaskDTO> saveTask(TaskDTO taskDTO, CustomUserDetails currentUser) {
        Optional<User> user = userRepository.findById(currentUser.getIdUsr());
        if (user.isPresent()) {
            Task task = new Task();

            modelMapper.map(taskDTO, task);
            Set<Task> userTasks = user.get().getTasks();
            userTasks.add(task);
            user.get().setTasks(userTasks);
            userRepository.save(user.get());
//            userRepository.merge(user.get());
            modelMapper.map(task, taskDTO);
            return Optional.of(taskDTO);
        }
        return Optional.empty();
    }

    public void deleteTask(Integer idTsk) {
        Task task = new Task();
        task.setIdTsk(idTsk);
        taskRepository.delete(task);
    }

}
