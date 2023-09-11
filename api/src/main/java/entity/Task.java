package entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.wildfly.common.annotation.NotNull;

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

  private Boolean done;

  // @Temporal(TemporalType.TIMESTAMP)
  // @Column(name = "created_at")
  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public void update(Task task) {
    this.setTitle(task.getTitle());
    this.setDescription(task.getDescription());
    this.setDone(task.getDone());
    this.setUpdatedAt(java.time.LocalDateTime.now());
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

  public Boolean getDone() {
    return done;
  }

  public void setDone(Boolean done) {
    this.done = done;
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
