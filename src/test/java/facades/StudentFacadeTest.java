package facades;

import dto.StudentDTO;
import entities.Student;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
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
    public void testGetStudentDTOById() throws Exception {
        StudentDTO exp = new StudentDTO(new Student("efg-567", "Rigmor Alfsen", "www.github.com/rigmor"));
        assertEquals(exp, facade.getStudentByDatabaseId(5));
    }

    @Test
    public void testGetStudentDTOByName() throws Exception {
        ArrayList<StudentDTO> exp = new ArrayList();
        exp.add(new StudentDTO(new Student("efg-567", "Rigmor Alfsen", "www.github.com/rigmor")));
        assertEquals(exp, facade.getStudentDTOByName("Rigmor Alfsen"));
    }

    @Test
    public void testGetStudentDTOByStudentID() throws Exception {
        StudentDTO exp = new StudentDTO(new Student("efg-567", "Rigmor Alfsen", "www.github.com/rigmor"));
        assertEquals(exp, facade.getStudentDTOByStudentID("efg-567"));
    }

    @Test
    public void testGetAllStudentDTO() {
        ArrayList<StudentDTO> exp = new ArrayList();
        exp.add(new StudentDTO(new Student("abc-123", "Ulrikke Jensen", "www.github.com/ulrikke")));
        exp.add(new StudentDTO(new Student("bcd-234", "Orla Hansen", "www.github.com/orla")));
        exp.add(new StudentDTO(new Student("cde-345", "Werner Bo", "www.github.com/werner")));
        exp.add(new StudentDTO(new Student("def-456", "Gerda Sørensen", "www.github.com/gerda")));
        exp.add(new StudentDTO(new Student("efg-567", "Rigmor Alfsen", "www.github.com/rigmor")));
        assertEquals(exp, facade.getAllStudentDTO());
    }
}
