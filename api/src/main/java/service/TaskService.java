package service;

import java.util.List;
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

  public List<Task> findAllTasks() {
    return taskRepository.findAll(Sort.by("createdAt", Direction.Descending)).list();
  }

  public void addTask(Task task) {
    task.setCreatedAt(java.time.LocalDateTime.now());
    taskRepository.persist(task);
  }

  public void changeStatus(Long id) throws ResourceNotFound {
    Task taskToUpdate = taskRepository.findById(id);
    if (taskToUpdate == null) {
      throw new ResourceNotFound();
    }

    taskToUpdate.setUpdatedAt(java.time.LocalDateTime.now());
    taskToUpdate.setDone(!taskToUpdate.getDone());

    taskRepository.persist(taskToUpdate);
  }

  // public Task findById(Long id) {
  // Optional<Task> optionalTask = taskRepository.findByIdOptional(id);
  // return optionalTask.orElse(null);
  // }

  public void deleteTask(Long id) throws ResourceNotFound {
    Task taskToDelete = taskRepository.findById(id);
    if (taskToDelete == null) {
      throw new ResourceNotFound();
    }
    taskRepository.deleteById(id);
  }

  public void updateTask(Long id, Task task) throws ResourceNotFound {
    Task taskToUpdate = taskRepository.findById(id);

    if (taskToUpdate == null) {
      throw new ResourceNotFound();
    }

    taskToUpdate.update(task);
    taskRepository.persist(taskToUpdate);
  }

}
