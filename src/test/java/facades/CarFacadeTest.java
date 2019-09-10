package facades;

import dto.CarDTO;
import entities.Car;
import java.util.ArrayList;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class CarFacadeTest {

    private static EntityManagerFactory emf;
    private static CarFacade facade;
    private Car c1 = null;
    private Car c2 = null;
    private Car c3 = null;
    private Car c4 = null;

    public CarFacadeTest() {
        c1 = new Car(1997, "Ford", "E350", 3000, "Fair condition", "René");
        c2 = new Car(1999, "Chevy", "Venture", 4900, "Fair condition", "Knud Åge");
        c3 = new Car(2000, "Chevy", "Venture", 5000, "Terrible condition", "Knud Åge");
        c4 = new Car(1996, "Jeep", "Grand Cherokee", 4799, 
                    "Good condition but has a lot of scratches", "Torben");
    }

    @BeforeAll
    public static void setUpClassV2() {
       emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST,Strategy.DROP_AND_CREATE);
       facade = CarFacade.getCarFacade(emf);
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("truncate table CA1_test.CAR").executeUpdate();
            em.persist(c1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(c2);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(c3);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(c4);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetCarCount() {
        assertEquals(4, facade.getCarCount(), "Expects four rows in the database");
    }

    @Test
    public void testGetAllCars()
    {
        System.out.println("getAllCars");
        List<CarDTO> expResult = new ArrayList<>();
        expResult.add(new CarDTO(c1));
        expResult.add(new CarDTO(c2));
        expResult.add(new CarDTO(c3));
        expResult.add(new CarDTO(c4));
        List<CarDTO> result = facade.getAllCars();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testFindCarByIDAllInfo()
    {
        System.out.println("findCarByIDAllInfo");
        Long id = 3L;
        Car expResult = c3;
        Car result = facade.findCarByIDAllInfo(id);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testFindCarByID()
    {
        System.out.println("findCarByID");
        Long id = 2L;
        CarDTO expResult = new CarDTO(c2);
        CarDTO result = facade.findCarByID(id);
        assertEquals(expResult, result);
    }
}