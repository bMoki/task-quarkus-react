package service;

import java.util.List;

import dto.TaskInputDTO;
import dto.TaskOutputDTO;
import entity.Task;
import errors.ResourceNotFound;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import repository.TaskRepository;

@ApplicationScoped
public class TaskService {

  @Inject
  TaskRepository taskRepository;

  public List<TaskOutputDTO> findAllTasks() {
    List<Task> tasks = taskRepository.findAll(Sort.by("createdAt", Direction.Descending)).list();
    List<TaskOutputDTO> taskOutputDTOs = tasks.stream().map(task -> new TaskOutputDTO(task)).toList();
    return taskOutputDTOs;
  }

  public void addTask(TaskInputDTO taskInputDTO) {
    Task task = new Task(taskInputDTO);
    taskRepository.persist(task);
  }

  public void changeStatus(Long id) throws ResourceNotFound {
    Task taskToUpdate = taskRepository.findById(id);
    if (taskToUpdate == null) {
      throw new ResourceNotFound();
    }

    taskToUpdate.changeStatus(taskToUpdate.getStatus());

    taskRepository.persist(taskToUpdate);
  }

  public void deleteTask(Long id) throws ResourceNotFound {
    Task taskToDelete = taskRepository.findById(id);
    if (taskToDelete == null) {
      throw new ResourceNotFound();
    }
    taskRepository.deleteById(id);
  }

  public void updateTask(Long id, TaskInputDTO taskInputDTO) throws ResourceNotFound {
    Task taskToUpdate = taskRepository.findById(id);
    Task task = new Task(taskInputDTO);

    if (taskToUpdate == null) {
      throw new ResourceNotFound();
    }

    taskToUpdate.update(task);
    taskRepository.persist(taskToUpdate);
  }

}
