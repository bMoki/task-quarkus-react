package entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.wildfly.common.annotation.NotNull;

import dto.TaskDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Task {

  public Task() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
  @SequenceGenerator(name = "sequence", sequenceName = "sequence")
  private Long id;

  @NotNull
  private String title;

  private String description;

  private Status status;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public Task(TaskDTO taskDTO) {
    this.title = taskDTO.getTitle();
    this.description = taskDTO.getDescription();
    this.status = taskDTO.getStatus();
    this.createdAt = taskDTO.getCreatedAt();
    this.updatedAt = taskDTO.getUpdatedAt();
  }

  public void update(Task task) {
    this.setTitle(task.getTitle());
    this.setDescription(task.getDescription());
    this.setStatus(task.getStatus());
    this.setUpdatedAt(java.time.LocalDateTime.now());
  }

  public void changeStatus(Status status) {
    if (status == Status.CONCLUIDA) {
      this.status = Status.PENDENTE;
    } else {
      this.status = Status.CONCLUIDA;
    }
    this.updatedAt = java.time.LocalDateTime.now();
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

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

}
