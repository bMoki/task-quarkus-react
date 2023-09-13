package entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.wildfly.common.annotation.NotNull;

import dto.TaskInputDTO;
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
  @Column(name = "concluded_at")
  private LocalDateTime concludedAt;

  public Task(TaskInputDTO taskDTO) {
    this.title = taskDTO.getTitle();
    this.description = taskDTO.getDescription();
    this.status = taskDTO.getStatus();
  }

  public void update(Task task) {
    this.title = task.getTitle();
    this.description = task.getDescription();
    if (this.status != Status.CONCLUIDA && task.getStatus() == Status.CONCLUIDA) {
      this.concludedAt = java.time.LocalDateTime.now();
    } else if (this.status != Status.PENDENTE && task.getStatus() == Status.PENDENTE) {
      this.concludedAt = null;
    }
    this.status = task.getStatus();
  }

  public void changeStatus(Status status) {
    if (status == Status.CONCLUIDA) {
      this.status = Status.PENDENTE;
      this.concludedAt = null;
    } else {
      this.status = Status.CONCLUIDA;
      this.concludedAt = java.time.LocalDateTime.now();
    }
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
