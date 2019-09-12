package rest;

import dto.JokeDTO;
import entities.Joke;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.io.IOException;
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
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
/**
 * Joke.java contains more than JokeDTO.java, therefore I test twice, to ensure
 * all information is serialized.
 *
 *
 * @author runin
 */
public class JokeResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    //Read this line from a settings-file  since used several places
    //private static final String TEST_DB = "jdbc:mysql://localhost:3307/startcode_test";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    List<Joke> jokes;
    List<JokeDTO> jokesDTO;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);

        //NOT Required if you use the version of EMF_Creator.createEntityManagerFactory used above        
        //System.setProperty("IS_TEST", TEST_DB);
        //We are using the database on the virtual Vagrant image, so username password are the same for all dev-databases
        httpServer = startServer();

        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;

        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        jokes = new ArrayList(); //init
        jokesDTO = new ArrayList(); //init

        //add to collection
        jokes.add(new Joke("A programmer puts two glasses on his bedside table before going to sleep. A full one, in case he gets thirsty, and an empty one, in case he doesn’t.", "https://redd.it/1kvhmz", "case-handling", 10));
        jokes.add(new Joke("A programmer is heading out to the grocery store, so his wife tells him \"get a gallon of milk, and if they have eggs, get a dozen.\" He returns with 13 gallons of milk.", "https://redd.it/1kvhmz", "numbers", 9));
        jokes.add(new Joke("What do programmers do before sex? Initialize <pre><code>for</code></pre>-play.", "https://redd.it/1kvhmz", "naughty", 7));
        jokes.add(new Joke("A programmer heads out to the store. His wife says \"while you're out, get some milk.\"", "https://redd.it/1kvhmz", "loops", 5));

        try {
            em.getTransaction().begin();
            Query query = em.createNativeQuery("truncate table CA1_test.JOKE;");
            query.executeUpdate();
            em.getTransaction().commit();
            for (Joke j : jokes) {
                em.getTransaction().begin();
                em.persist(j);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }

        jokes.forEach(e -> jokesDTO.add(new JokeDTO(e)));
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/jokes").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/jokes").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("ACCESS GRANTED"));
    }

    @Test
    public void testGetJokeCount() throws Exception {
        given()
                .contentType("application/json")
                .get("/jokes/count").then()//.log().body()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(jokes.size()));
    }

    @Test
    public void testGetAllJokes() throws Exception {
        given()
                .contentType("application/json")
                .get("/jokes/all").then()//.log().body()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("[0].reference", equalTo(jokes.get(0).getReference()))
                .body("[1].reference", equalTo(jokes.get(1).getReference()))
                .body("[2].type", equalTo(jokes.get(2).getType()))
                .body("[3].type", equalTo(jokes.get(3).getType()));
    }

    @Test
    public void testGetAllJokesAsDTO() throws Exception {
        given()
                .contentType("application/json")
                .get("/jokes/all/dto").then()//.log().body()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("[0].joke", equalTo(jokesDTO.get(0).getJoke()))
                .body("[1].joke", equalTo(jokesDTO.get(1).getJoke()))
                .body("[2].rating", equalTo(jokesDTO.get(2).getRating()))
                .body("[3].rating", equalTo(jokesDTO.get(3).getRating()));
    }

    @Test
    public void testGetJokeByID_SIMPLE() throws Exception {
        given()
                .contentType("application/json")
                .get("/jokes/{id}", jokes.get(1).getId()).then()//.log().body()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("joke", equalTo(jokes.get(1).getJoke()));
    }

    @Test
    public void testGetJokeByID_ADVANCED() throws Exception {
        Joke result = get("jokes/{id}", jokes.get(3).getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .as(Joke.class);

        MatcherAssert.assertThat((result), equalTo(jokes.get(3)));
    }

    @Test
    public void testGetJokeByIDError() throws Exception {
        given()
                .contentType("application/json")
                .get("/jokes/{id}", 999).then()//.log().body()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("error", equalTo("Could not get joke by ID -> Database is empty or joke doesn't exist."));
    }

    @Test
    public void testGetJokeByIDAsDTO_SIMPLE() throws Exception {
        given()
                .contentType("application/json")
                .get("/jokes/{id}/dto", jokesDTO.get(2).getId()).then()//.log().body()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("joke", equalTo(jokesDTO.get(2).getJoke()));
    }

    @Test
    public void testGetJokeByIDAsDTO_ADVANCED() throws Exception {
        JokeDTO result = get("jokes/{id}/dto", jokesDTO.get(3).getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .as(JokeDTO.class);

        MatcherAssert.assertThat((result), equalTo(jokesDTO.get(3)));
    }

    @Test
    public void testGetJokeByIDAsDTOError() throws Exception {
        given()
                .contentType("application/json")
                .get("/jokes/{id}/dto", 888).then()//.log().body()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("error", equalTo("Could not get joke (DTO) by ID -> Database is empty or joke doesn't exist."));
    }

    @Test
    public void testGetJokeByRandom() throws Exception {
        given()
                .contentType("application/json")
                .get("/jokes/random").then()//.log().body()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", notNullValue())
                .body("joke", notNullValue())
                .body("reference", notNullValue())
                .body("type", notNullValue())
                .body("rating", notNullValue());
    }
    
        @Test
    public void testGetJokeDTOByRandom() throws Exception {
        given()
                .contentType("application/json")
                .get("/jokes/random/dto").then()//.log().body()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", notNullValue())
                .body("joke", notNullValue())
                .body("$", not(hasKey("reference")))
                .body("$", not(hasKey("type")))
                .body("rating", notNullValue());
    }

//    public static void main(String[] args) throws IOException {
//        HttpServer server = startServer();
//        System.in.read();
//        server.shutdownNow();
//        System.out.println("done");
//    }

}
