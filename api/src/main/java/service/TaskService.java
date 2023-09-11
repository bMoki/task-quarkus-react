package service;

import java.util.List;

import dto.TaskDTO;
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

  public List<TaskDTO> findAllTasks() {
    List<Task> tasks = taskRepository.findAll(Sort.by("createdAt", Direction.Descending)).list();
    List<TaskDTO> taskDTOs = tasks.stream().map(task -> new TaskDTO(task)).toList();
    return taskDTOs;
  }

  public void addTask(TaskDTO taskDTO) {
    Task task = new Task(taskDTO);
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

  public void updateTask(Long id, TaskDTO taskDTO) throws ResourceNotFound {
    Task taskToUpdate = taskRepository.findById(id);
    Task task = new Task(taskDTO);

    if (taskToUpdate == null) {
      throw new ResourceNotFound();
    }

    taskToUpdate.update(task);
    taskRepository.persist(taskToUpdate);
  }

}
