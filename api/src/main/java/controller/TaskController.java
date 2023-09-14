package controller;

import java.util.ArrayList;
import java.util.List;

import dto.TaskInputDTO;
import dto.TaskOutputDTO;
import errors.ResourceNotFound;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import service.TaskService;

@Path("/api/task")
public class TaskController {

  @Inject
  TaskService taskService;

  @GET
  public Response getAllTasks() {
    List<TaskOutputDTO> tasks = new ArrayList<>();

    try {
      tasks = taskService.findAll();
    } catch (Exception e) {
      return Response.serverError().build();
    }
    return Response.ok(tasks).build();
  }

  @GET
  @Path("/{id}")
  public Response findTaskById(@PathParam("id") Long id) {
    TaskOutputDTO task = new TaskOutputDTO();
    try {
      task = taskService.findById(id);
    } catch (Exception e) {
      if (e instanceof ResourceNotFound) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
      return Response.serverError().build();
    }
    return Response.ok(task).build();
  }

  @POST
  @Transactional
  public Response addTask(@Valid TaskInputDTO taskDTO) {
    try {
      taskService.addTask(taskDTO);
    } catch (Exception e) {
      return Response.serverError().build();
    }
    return Response.noContent().build();
  }

  @PATCH
  @Path("/status/{id}")
  @Transactional
  public Response changeStatus(@PathParam("id") Long id) {
    try {
      taskService.changeStatus(id);
    } catch (Exception e) {
      if (e instanceof ResourceNotFound) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
      return Response.serverError().build();
    }
    return Response.ok().build();
  }

  @PUT
  @Path("/{id}")
  @Transactional
  public Response updateTask(@PathParam("id") Long id, @Valid TaskInputDTO task) {
    try {
      taskService.updateTask(id, task);
    } catch (Exception e) {
      if (e instanceof ResourceNotFound) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
      return Response.serverError().build();
    }
    return Response.ok().build();
  }

  @DELETE
  @Path("/{id}")
  @Transactional
  public Response deleteTask(@PathParam("id") Long id) {
    try {
      taskService.deleteTask(id);
    } catch (Exception e) {
      if (e instanceof ResourceNotFound) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
      return Response.serverError().build();
    }
    return Response.ok().build();
  }
}
