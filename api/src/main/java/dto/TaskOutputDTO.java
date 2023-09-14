package dto;

import java.time.LocalDateTime;

import entity.Status;
import entity.Task;

public class TaskOutputDTO {
  private Long id;
  private String title;
  private String description;
  private Status status;
  private LocalDateTime createdAt;
  private LocalDateTime concludedAt;

  public TaskOutputDTO(Task task) {
    this.id = task.getId();
    this.title = task.getTitle();
    this.description = task.getDescription();
    this.status = task.getStatus();
    this.createdAt = task.getCreatedAt();
    this.concludedAt = task.getConcludedAt();
  }

  public TaskOutputDTO() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getConcludedAt() {
    return concludedAt;
  }

  public void setConcludedAt(LocalDateTime concludedAt) {
    this.concludedAt = concludedAt;
  }

}
