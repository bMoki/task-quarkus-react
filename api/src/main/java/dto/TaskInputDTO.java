package dto;

import entity.Status;
import entity.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskInputDTO {

  @NotNull(message = "Title must be informed")
  @NotBlank(message = "Title can't be blank")
  private String title;

  @NotNull(message = "Description must be informed")
  @NotBlank(message = "Description can't be blank")
  private String description;

  @NotNull(message = "Status must be informed")
  private Status status;

  public TaskInputDTO() {
  }

  public TaskInputDTO(Task task) {
    this.title = task.getTitle();
    this.description = task.getDescription();
    this.status = task.getStatus();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

}
