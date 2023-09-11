package controller;

import java.util.ArrayList;
import java.util.List;

import entity.Task;
import errors.ResourceNotFound;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
    List<Task> tasks = new ArrayList<>();

    try {
      tasks = taskService.findAllTasks();
    } catch (Exception e) {
      e.printStackTrace();
      return Response.serverError().build();
    }
    return Response.ok(tasks).build();
  }

  @POST
  @Transactional
  public Response addTask(Task task) {
    try {
      taskService.addTask(task);
    } catch (Exception e) {
      e.printStackTrace();
      return Response.serverError().build();
    }
    return Response.noContent().build();
  }

  // @GET
  // @Path("/{id}")
  // public Response getTask(@PathParam("id") Long id) {
  // Task task = taskService.findById(id);
  // if (task == null) {
  // throw new WebApplicationException(404);
  // }
  // return Response.ok(task).build();
  // }

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
  public Response updateTask(@PathParam("id") Long id, Task task) {
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
