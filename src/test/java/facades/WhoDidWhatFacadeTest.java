package facades;

import dto.WhoDidWhatDTO;
import entities.WhoDidWhat;
import java.util.ArrayList;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class WhoDidWhatFacadeTest {

    private static EntityManagerFactory emf;
    private WhoDidWhatFacade facade;
    private WhoDidWhat testTask;
    private List<WhoDidWhat> testList;

    public WhoDidWhatFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);

    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
        facade = WhoDidWhatFacade.getWhoDidWhatFacade(emf);

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

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void testGetWorkDoneByName() throws Exception {
        System.out.println("Get Work Done by Name Test: ");
        // Arrange
        WhoDidWhatDTO expResult = new WhoDidWhatDTO(testTask);
        // Act
        WhoDidWhatDTO result = facade.getWorkDoneByName("Malte");
        // Assert
        assertNotNull(result);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetWorkDoneByWrongName() throws Exception {
        System.out.println("Get Work Done By Wrong Name Test: ");
        // Arrange
        Throwable expResult = new IllegalArgumentException("No Student found by that name.");
        // Act
        Throwable result = assertThrows(IllegalArgumentException.class, () -> {
            facade.getWorkDoneByName("WRONG NAME");
        });
        // Assert
        assertNotNull(result);
        assertEquals(expResult.getCause(), result.getCause());
    }

    @Test
    public void testGetAllWorkDone() throws Exception {
        System.out.println("Get All Work Done Test: ");
        // Arrange
        List<WhoDidWhatDTO> expResult = new ArrayList<>();
        for (WhoDidWhat whodidwhat : testList) {
            expResult.add(new WhoDidWhatDTO(whodidwhat));
        }
        // Act
        List<WhoDidWhatDTO> result = facade.getAllWorkDone();
        // Assert
        assertNotNull(result);
        assertEquals(expResult, result);
    }

    @Test
    public void testMakeWorkByNewName() throws Exception {
        System.out.println("Make Work By New Name Test: ");
        // Arrange
        WhoDidWhat expResult = new WhoDidWhat("Test");
        expResult.addDone("Everything");
        // Act
        WhoDidWhat result = facade.makeWork("Test", "Everything");
        // Assert
        assertNotNull(result);
        assertEquals(expResult, result);
    }

    @Test
    public void testMakeWorkByExistingName() throws Exception {
        System.out.println("Make Work By Existing Name Test: ");
        // Arrange
        WhoDidWhat expResult = new WhoDidWhat("existing");
        expResult.addDone("work");
        expResult.addDone("more work");
        // Act
        WhoDidWhat result = facade.makeWork("existing", "more work");
        // Assert
        assertNotNull(result);
        assertEquals(expResult, result);
    }

}
