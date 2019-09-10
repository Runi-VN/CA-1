package facades;

import dto.WhoDidWhatDTO;
import entities.WhoDidWhat;
import java.util.ArrayList;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class WhoDidWhatFacadeTest {

    private static EntityManagerFactory emf;
    private static WhoDidWhatFacade facade;
    private static WhoDidWhat testTask;
    private static List<WhoDidWhat> testList = new ArrayList<>();

    public WhoDidWhatFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = WhoDidWhatFacade.getWhoDidWhatFacade(emf);

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
            for (WhoDidWhat obj : testList) {
                em.getTransaction().begin();
                em.persist(obj);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    @Transactional
    public void testGetWorkDoneByName() throws Exception {
        // Arrange
        WhoDidWhatDTO expResult = new WhoDidWhatDTO(testTask);
        // Act
        WhoDidWhatDTO result = facade.getWorkDoneByName("Malte");
        // Assert
        assertEquals(expResult, result);

    }

    @Test
    @Transactional
    public void testGetAllWorkDone() throws Exception {
        // Arrange
        List<WhoDidWhatDTO> expResult = new ArrayList<>();
        for (WhoDidWhat whodidwhat : testList) {
            expResult.add(new WhoDidWhatDTO(whodidwhat));
        }
        // Act
        List<WhoDidWhatDTO> result = facade.getAllWorkDone();
        // Assert
        assertEquals(expResult, result);
    }

    @Test
    @Transactional
    public void testMakeWorkByNewName() throws Exception {
        // Arrange
        WhoDidWhat expResult = new WhoDidWhat("Test");
        expResult.addDone("Everything");
        // Act
        WhoDidWhat result = facade.makeWork("Test", "Everything");
        // Assert
        assertEquals(expResult, result);
    }
    
    @Test
    @Transactional
    public void testMakeWorkByExistingName() throws Exception {
        // Arrange
        WhoDidWhat expResult = new WhoDidWhat("existing");
        expResult.addDone("work");
        expResult.addDone("more work");
        // Act
        WhoDidWhat result = facade.makeWork("existing", "more work");
        // Assert
        assertEquals(expResult, result);
    }

}
