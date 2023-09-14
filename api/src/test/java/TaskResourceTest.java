import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dto.TaskOutputDTO;
import entity.Status;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import io.quarkus.test.junit.QuarkusTest;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskResourceTest {

  @Test
  @Order(1)
  public void mustCreateTaskTest() {
    JsonObject json = Json.createObjectBuilder()
        .add("title", "someTitle")
        .add("description", "someDescription")
        .add("status", Status.PENDENTE.toString())
        .build();

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(json.toString())
        .when()
        .post("/api/task")
        .then()
        .statusCode(204);
  }

  @Test
  @Order(1)
  public void mustGetBadRequestErrorCreatingTaskTest() {
    String wrongAtribute = "Wrong";
    JsonObject json = Json.createObjectBuilder()
        .add(wrongAtribute, "someTitle")
        .add("description", "someDescription")
        .add("status", Status.PENDENTE.toString())
        .build();

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(json.toString())
        .when()
        .post("/api/task")
        .then()
        .statusCode(400);
  }

  @Test
  @Order(2)
  public void mustGetTasksByIdTest() {
    given()
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("/api/task/1")
        .then()
        .statusCode(200);
  }

  @Test
  @Order(2)
  public void mustGetNotFoundErrorGettingTasksByIdTest() {
    Long nonExistingId = 8L;
    given()
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("/api/task/" + nonExistingId)
        .then()
        .statusCode(404);
  }

  @Test
  @Order(2)
  public void mustGetAllTasksTest() {
    given()
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("/api/task")
        .then()
        .statusCode(200)
        .assertThat().body("size()", greaterThan(0));
  }

  @Test
  @Order(2)
  public void mustGetNotFoundErrorChangingTaskStatusTest() {
    Long nonExistingId = 8L;
    given()
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .patch("/api/task/status/" + nonExistingId)
        .then()
        .statusCode(404);
  }

  @Test
  @Order(2)
  public void mustChangeTaskStatusToConcludedTest() {
    given()
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .patch("/api/task/status/1")
        .then()
        .statusCode(200);

    TaskOutputDTO taskOutputDTO = given()
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("/api/task/1")
        .then()
        .statusCode(200)
        .extract()
        .body().as(TaskOutputDTO.class);

    assertNotNull(taskOutputDTO);
    assertEquals(Status.CONCLUIDA, taskOutputDTO.getStatus());
    assertNotNull(taskOutputDTO.getConcludedAt());
  }

  @Test
  @Order(3)
  public void mustChangeTaskStatusToPendentTest() {
    given()
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .patch("/api/task/status/1")
        .then()
        .statusCode(200);

    TaskOutputDTO taskOutputDTO = given()
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("/api/task/1")
        .then()
        .statusCode(200)
        .extract()
        .body().as(TaskOutputDTO.class);

    assertNotNull(taskOutputDTO);
    assertEquals(Status.PENDENTE, taskOutputDTO.getStatus());
    assertNull(taskOutputDTO.getConcludedAt());
  }

  @Test
  @Order(4)
  public void mustUpdateTaskTest() {
    String newTitle = "Updated";

    JsonObject json = Json.createObjectBuilder()
        .add("title", newTitle)
        .add("description", "someDescription")
        .add("status", Status.PENDENTE.toString())
        .build();

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(json.toString())
        .when()
        .put("/api/task/1")
        .then()
        .statusCode(200);

    TaskOutputDTO taskOutputDTO = given()
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("/api/task/1")
        .then()
        .statusCode(200)
        .extract()
        .body().as(TaskOutputDTO.class);

    assertNotNull(taskOutputDTO);
    assertEquals(newTitle, taskOutputDTO.getTitle());
  }

  @Test
  @Order(4)
  public void mustGetNotFoundErrorUpdatingTaskTest() {
    Long nonExistingId = 8L;

    JsonObject json = Json.createObjectBuilder()
        .add("title", "newTitle")
        .add("description", "someDescription")
        .add("status", Status.PENDENTE.toString())
        .build();

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(json.toString())
        .when()
        .put("/api/task/" + nonExistingId)
        .then()
        .statusCode(404);
  }

  @Test
  @Order(4)
  public void mustGetBadRequestErrorUpdatingTaskTest() {
    String wrongAtribute = "Wrong";

    JsonObject json = Json.createObjectBuilder()
        .add(wrongAtribute, "newTitle")
        .add("description", "someDescription")
        .add("status", Status.PENDENTE.toString())
        .build();

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(json.toString())
        .when()
        .put("/api/task/1")
        .then()
        .statusCode(400);
  }

  @Test
  @Order(5)
  public void mustDeleteTaskTest() {
    given()
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .delete("/api/task/1")
        .then()
        .statusCode(200);

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("/api/task")
        .then()
        .statusCode(200)
        .assertThat().body("size()", lessThan(1));
  }

  @Test
  @Order(5)
  public void mustGetNotFoundErrorDeletingTaskTest() {
    Long nonExistingId = 8L;
    given()
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .delete("/api/task/" + nonExistingId)
        .then()
        .statusCode(404);
  }

}
