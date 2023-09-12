import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

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
  public void testAddTask() {
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
  @Order(2)
  public void testGetAllTasks() {
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
  public void testChangeTaskStatus() {
    given()
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .patch("/api/task/status/1")
        .then()
        .statusCode(200);
  }

  @Test
  @Order(2)
  public void testUpdateTask() {
    JsonObject json = Json.createObjectBuilder()
        .add("title", "Updated")
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
  }

  @Test
  @Order(2)
  public void testDeleteTask() {
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

}
