package rest;

import dto.WhoDidWhatDTO;
import entities.WhoDidWhat;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class WhoDidWhatResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    WhoDidWhat testTask;
    List<WhoDidWhat> testList;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
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
        testList = new ArrayList<>();

        testTask = new WhoDidWhat("Malte");
        testTask.addDone("This");
        testTask.addDone("That");
        testTask.addDone("The other");

        WhoDidWhat testTwo = new WhoDidWhat("Asger");
        testTwo.addDone("Everything");
        testTwo.addDone("Even more");
        testTwo.addDone("So much");

        WhoDidWhat testExisting = new WhoDidWhat("existing");
        testExisting.addDone("work");

        testList.add(testTask);
        testList.add(testTwo);
        testList.add(testExisting);

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Query query = em.createNativeQuery("truncate table CA1_test.WHODIDWHAT;");
            query.executeUpdate();
            em.getTransaction().commit();
            for (WhoDidWhat obj : testList) {
                em.getTransaction().begin();
                em.persist(obj);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetWorkByName() throws Exception {
        WhoDidWhatDTO result = get("work/{name}", testTask.getName()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .as(WhoDidWhatDTO.class);

        MatcherAssert.assertThat((result), equalTo(new WhoDidWhatDTO(testTask)));
    }

    @Test
    public void testGetJokeByIDError() throws Exception {
        given()
                .contentType("application/json")
                .get("work/{name}", "WRONG NAME").then().log().body()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("error", equalTo("No Student found by that name."));
    }

    @Test
    public void testGetAllWork() throws Exception {
        given()
                .contentType("application/json")
                .get("work/all").then().log().body()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("[0].name", equalTo(testList.get(0).getName()))
                .body("[1].name", equalTo(testList.get(1).getName()))
                .body("[0].done", equalTo(testList.get(0).getDone()))
                .body("[1].done", equalTo(testList.get(1).getDone()));
    }

}
