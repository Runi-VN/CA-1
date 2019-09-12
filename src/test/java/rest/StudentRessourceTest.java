package rest;

import entities.Student;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author Camilla
 */
public class StudentRessourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.CREATE);
        httpServer = startServer();

        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;

        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Query query = em.createNativeQuery("truncate table CA1_test.STUDENT;");
        query.executeUpdate();
        em.getTransaction().commit();

        ArrayList<Student> allStudents = new ArrayList();
        allStudents.add(new Student("abc-123", "Ulrikke Jensen", "www.github.com/ulrikke"));
        allStudents.add(new Student("bcd-234", "Orla Hansen", "www.github.com/orla"));
        allStudents.add(new Student("cde-345", "Werner Bo", "www.github.com/werner"));
        allStudents.add(new Student("def-456", "Gerda Sørensen", "www.github.com/gerda"));
        allStudents.add(new Student("efg-567", "Rigmor Alfsen", "www.github.com/rigmor"));
        try {
            for (Student s : allStudents) {
                em.getTransaction().begin();
                em.persist(s);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/students").then().statusCode(200);
    }

    @Test
    public void testStudentsRoot() throws Exception {
        given()
                .contentType("application/json").when()
                .get("/students").then().assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("path students succesful"));
    }

//    @Test
//    public void testData() {
//        given()
//                .contentType("application/json").when()
//                .get("/students/data").then().assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("dataMsg", equalTo("Students created"));
//    }

    @Test
    public void testGetAllStudents() throws Exception {
        given()
                .contentType("application/json").when()
                .get("/students/allstudents").then().assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("[0].studentID", equalTo("abc-123"))
                .body("[0].name", equalTo("Ulrikke Jensen"))
                .body("[0].github", equalTo("www.github.com/ulrikke"))
                .body("[1].studentID", equalTo("bcd-234"))
                .body("[1].name", equalTo("Orla Hansen"))
                .body("[1].github", equalTo("www.github.com/orla"))
                .body("[4].studentID", equalTo("efg-567"))
                .body("[4].name", equalTo("Rigmor Alfsen"))
                .body("[4].github", equalTo("www.github.com/rigmor"))
                .body("size()", is(5));
    }

    @Test
    public void testGetStudentDTOByStudentID() throws Exception {
        given()
                .contentType("application/json")
                .get("/students/studentid/efg-567").then()
                .assertThat().statusCode(HttpStatus.OK_200.getStatusCode())
                .body("studentID", equalTo("efg-567"))
                .body("name", equalTo("Rigmor Alfsen"))
                .body("github", equalTo("www.github.com/rigmor"));
    }

    @Test
    public void testGetStudentByDatabaseID() throws Exception {
        given()
                .contentType("application/json")
                .get("/students/databaseid/5").then()
                .assertThat().statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", equalTo(5))
                .body("studentID", equalTo("efg-567"))
                .body("name", equalTo("Rigmor Alfsen"))
                .body("github", equalTo("www.github.com/rigmor"));

    }

    @Test
    public void testGetStudentDTOByName() throws Exception {
        given()
                .contentType("application/json")
                .get("/students/studentname/Rigmor Alfsen").then()
                .assertThat().statusCode(HttpStatus.OK_200.getStatusCode())
                .body("[0].studentID", equalTo("efg-567"))
                .body("[0].name", equalTo("Rigmor Alfsen"))
                .body("[0].github", equalTo("www.github.com/rigmor"))
                .body("size()", is(1));
    }
}