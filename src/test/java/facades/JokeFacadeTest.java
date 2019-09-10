package facades;

import dto.JokeDTO;
import entities.Joke;
import java.util.ArrayList;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class JokeFacadeTest {

    private static EntityManagerFactory emf;
    private static JokeFacade facade;
    private static List<Joke> jokes;

    public JokeFacadeTest() {
    }

    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = JokeFacade.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        jokes = new ArrayList(); //init

        //add to collection
        jokes.add(new Joke("A programmer puts two glasses on his bedside table before going to sleep. A full one, in case he gets thirsty, and an empty one, in case he doesn’t.", "https://redd.it/1kvhmz", "case-handling", 10));
        jokes.add(new Joke("A programmer is heading out to the grocery store, so his wife tells him \"get a gallon of milk, and if they have eggs, get a dozen.\" He returns with 13 gallons of milk.", "https://redd.it/1kvhmz", "numbers", 9));
        jokes.add(new Joke("What do programmers do before sex? Initialize <pre><code>for</code></pre>-play.", "https://redd.it/1kvhmz", "naughty", 7));
        jokes.add(new Joke("A programmer heads out to the store. His wife says \"while you're out, get some milk.\"", "https://redd.it/1kvhmz", "loops", 5));

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Joke.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
            for (Joke j : jokes) {
                em.getTransaction().begin();
                em.persist(j);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        //Remove any data after each test was run
    }

    @Test
    public void testGetJokeCount() {
        assertEquals(jokes.size(), facade.getJokeCount(), "Expects three rows in the database");
    }

    @Test
    public void testGetAllJokes() {
        //Arrange
        List<Joke> expResult = jokes;
        List<Joke> result;

        //Act
        result = facade.getAllJokes();

        //Assert
        Assertions.assertNotNull(result);
        assertEquals(expResult, result);
    }

    @Disabled
    @Test
    public void testGetAllJokesError() {
        //Arrange

        //Act
        //Assert
    }

    @Test
    public void testGetAllJokesAsDTO() {
        //Arrange
        List<JokeDTO> expResult = new ArrayList();
        List<Joke> result;

        for (Joke j : jokes) {
            expResult.add(new JokeDTO(j)); //adding DTOs
        }

        //Act
        result = facade.getAllJokesAsDTO();

        //Assert
        Assertions.assertNotNull(result);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetJokeById() {
        //Arrange
        Joke expResult = jokes.get(2);
        Joke result;

        //Act
        result = facade.getJokeById(expResult.getId());

        //Assert
        Assertions.assertNotNull(result);
        assertEquals(expResult, result);
    }

    /**
     * em.find in getJokeByID does not actually throw an exception, but returns null.
     */
    @Test
    public void testGetJokeByIdError() {
        //Arrange
        Joke expResult = null;
        Joke result;

        //Act
        result = facade.getJokeById(99L);

        //Assert
        Assertions.assertNull(result);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetJokeByIdAsDTO() {
        //Arrange

        //Act
        //Assert
    }

    @Test
    public void testGetJokeRandom() {
        //Arrange

        //Act
        //Assert
    }

    @Test
    public void testGetJokeRandomError() {
        //Arrange

        //Act
        //Assert
    }

    @Test
    public void testGetJokeRandomAsDTO() {
        //Arrange

        //Act
        //Assert
    }

}
