package facades;

import dto.StudentDTO;
import entities.Student;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.EMF_Creator;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

/**
 *
 * @author Camilla
 */
public class StudentFacadeTest {

    private static EntityManagerFactory emf;
    private static StudentFacade facade;

    public StudentFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = StudentFacade.getStudentFacade(emf);
    }

    @BeforeEach
    public void setUp() {

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Query query = em.createNativeQuery("truncate table startcode_test.MOVIE;");
        query.executeUpdate();
        em.getTransaction().commit();

        // Student(String studentID, String name, String github)
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

    /**
     * Test of getStudentDTOById method, of class StudentFacade.
     */
    @Test
    public void testGetStudentDTOById() throws Exception {
        System.out.println("getStudentDTOById");
        long id = 0L;
        StudentFacade instance = null;
        StudentDTO expResult = null;
        StudentDTO result = instance.getStudentDTOById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStudentDTOByName method, of class StudentFacade.
     */
    @Test
    public void testGetStudentDTOByName() throws Exception {
        System.out.println("getStudentDTOByName");
        String name = "";
        StudentFacade instance = null;
        List<StudentDTO> expResult = null;
        List<StudentDTO> result = instance.getStudentDTOByName(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStudentDTOByStudentID method, of class StudentFacade.
     */
    @Test
    public void testGetStudentDTOByStudentID() throws Exception {
        System.out.println("getStudentDTOByStudentID");
        String studentID = "";
        StudentFacade instance = null;
        StudentDTO expResult = null;
        StudentDTO result = instance.getStudentDTOByStudentID(studentID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllStudentDTO method, of class StudentFacade.
     */
    @Test
    public void testGetAllStudentDTO() {
        System.out.println("getAllStudentDTO");
        StudentFacade instance = null;
        List<StudentDTO> expResult = null;
        List<StudentDTO> result = instance.getAllStudentDTO();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
