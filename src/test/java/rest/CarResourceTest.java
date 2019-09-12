package rest;

import dto.CarDTO;
import entities.Car;
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
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled

public class CarResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    //Read this line from a settings-file  since used several places
    //private static final String TEST_DB = "jdbc:mysql://localhost:3307/CA1_test";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    
    private Car c1 = null;
    private Car c2 = null;
    private Car c3 = null;
    private Car c4 = null;
    private Car c5 = null;
    private final List<Car> cars; 
    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    public CarResourceTest(){
        c1 = new Car(1997, "Ford", "E350", 3000, "Fair condition", "René");
        c2 = new Car(1999, "Chevy", "Venture", 4900, "Fair condition", "Knud Åge");
        c3 = new Car(2000, "Chevy", "Venture", 5000, "Terrible condition", "Knud Åge");
        c4 = new Car(1996, "Jeep", "Grand Cherokee", 4799, 
                    "Good condition but has a lot of scratches", "Torben");
        c5 = new Car(2012, "Pagani", "Zonda", 6544999.35, "Basically new", "Johnny Ringo");
        cars = new ArrayList<>();
        cars.add(c1);
        cars.add(c2);
        cars.add(c3);
        cars.add(c4);
        cars.add(c5);
    }
    
    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);

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
        try {
            em.getTransaction().begin();
            Query query = em.createNativeQuery("truncate table CA1_test.CAR;");
            query.executeUpdate();
            em.getTransaction().commit();
            for (Car c : cars) {
                em.getTransaction().begin();
                em.persist(c);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }

    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/car").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/car").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("We're selling cars!"));
    }

    @Test
    public void testGetCarCount() throws Exception {
        given()
                .contentType("application/json")
                .get("/car/count").then().log().body()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo("There are "+cars.size()+" cars for sale"));
    }

    @Test
    public void testGetAllCars() throws Exception {
        given()
                .contentType("application/json")
                .get("/car/all").then().log().body()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                //Can't test using Long
                .body("[0].ID", equalTo(Integer.parseInt(""+c1.getId())))
                .body("[1].Year", equalTo(c2.getCar_year()))
                .body("[2].Make", equalTo(c3.getCar_make()))
                .body("[3].Model", equalTo(c4.getCar_model()))
                .body("[4].Price", is(6544999.35f));
    }

    @Test
    public void testGetCarByID() throws Exception {
        given()
                .contentType("application/json")
                .get("/car/{id}", 1)
                .then().log().body()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("ID", Matchers.is(1));
    }

    @Test
    public void testGetCarByID_ADVANCED() throws Exception {
        CarDTO result = get("/car/{id}", c2.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .as(CarDTO.class);

        MatcherAssert.assertThat(result, equalTo(new CarDTO(c2)));
    }

    @Test
    public void testNegativeGetCarByID() throws Exception {
        given()
                .contentType("application/json")
                .get("/car/{id}", 21).then().log().body()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("The car doesn't exist or we failed to retrieve it!"));
    }

    @Test
    public void testGetCarByIDAllInfo() throws Exception {
        given()
                .contentType("application/json")
                .get("/car/{id}/allInfo", c5.getId()).then().log().body()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("car_current_owner", equalTo(c5.getCar_current_owner()))
                .body("car_condition", equalTo(c5.getCar_condition()));
    }

    @Test
    public void testGetCarByIDAllInfo_ADVANCED() throws Exception {
        Car result = get("car/{id}/allInfo", c4.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .as(Car.class);

        MatcherAssert.assertThat((result), equalTo(c4));
    }

    @Test
    public void testNegativeGetCarByIDAllInfo() throws Exception {
        given()
                .contentType("application/json")
                .get("/car/{id}/allInfo", 21).then().log().body()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("The car doesn't exist or we failed to retrieve it!"));
    }

}
