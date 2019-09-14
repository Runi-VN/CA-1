package facades;

import dto.StudentDTO;
import dto.StudentDTOcolor;
import entities.Student;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.junit.jupiter.api.Assertions;
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
        allStudents.add(new Student("abc-123", "Ulrikke Jensen", "www.github.com/ulrikke", "red"));
        allStudents.add(new Student("bcd-234", "Orla Hansen", "www.github.com/orla", "red"));
        allStudents.add(new Student("cde-345", "Werner Bo", "www.github.com/werner", "red"));
        allStudents.add(new Student("def-456", "Gerda Sørensen", "www.github.com/gerda", "red"));
        allStudents.add(new Student("efg-567", "Rigmor Alfsen", "www.github.com/rigmor", "red"));
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
    public void testGetStudentByDatabaseId() throws Exception {
        Student exp = new Student("efg-567", "Rigmor Alfsen", "www.github.com/rigmor", "red");
        assertEquals(exp, facade.getStudentByDatabaseId(5));
    }

    @Test
    public void testGetStudentByDatabaseId_Error() throws Exception {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            facade.getStudentByDatabaseId(10);
        });
    }

    @Test
    public void testGetStudentDTOByName() throws Exception {
        ArrayList<StudentDTO> exp = new ArrayList();
        exp.add(new StudentDTO(new Student("efg-567", "Rigmor Alfsen", "www.github.com/rigmor", "red")));
        assertEquals(exp, facade.getStudentDTOByName("Rigmor Alfsen"));
    }

    @Test
    public void testGetStudentDTOByName_Error() throws Exception {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            facade.getStudentDTOByName("");
        });
    }

    @Test
    public void testGetStudentDTOByStudentID() throws Exception {
        StudentDTO exp = new StudentDTO(new Student("efg-567", "Rigmor Alfsen", "www.github.com/rigmor", "red"));
        assertEquals(exp, facade.getStudentDTOByStudentID("efg-567"));
    }

    @Test
    public void testGetStudentDTOByStudentID_Error() throws Exception {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            facade.getStudentDTOByStudentID("");
        });
    }

    @Test
    public void testGetAllStudentDTO() {
        ArrayList<StudentDTO> exp = new ArrayList();
        exp.add(new StudentDTO(new Student("abc-123", "Ulrikke Jensen", "www.github.com/ulrikke", "red")));
        exp.add(new StudentDTO(new Student("bcd-234", "Orla Hansen", "www.github.com/orla", "red")));
        exp.add(new StudentDTO(new Student("cde-345", "Werner Bo", "www.github.com/werner", "red")));
        exp.add(new StudentDTO(new Student("def-456", "Gerda Sørensen", "www.github.com/gerda", "red")));
        exp.add(new StudentDTO(new Student("efg-567", "Rigmor Alfsen", "www.github.com/rigmor", "red")));
        assertEquals(exp, facade.getAllStudentDTO());
    }

    @Test
    public void testGetAllStudentDTO_Error() {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Query query = em.createNativeQuery("truncate table CA1_test.STUDENT;");
        query.executeUpdate();
        em.getTransaction().commit();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            facade.getAllStudentDTO();
        });
    }

    @Test
    public void testGetStudentDTOcolorByStudentID() throws Exception {
        StudentDTOcolor exp = new StudentDTOcolor(new Student("efg-567", "Rigmor Alfsen", "www.github.com/rigmor", "red"));
        assertEquals(exp, facade.getStudentDTOcolorByStudentID("efg-567"));
    }

    @Test
    public void testGetStudentDTOcolorByStudentID_Error() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            facade.getStudentDTOcolorByStudentID("");
        });
    }

    @Test
    public void testGetAllStudentDTOcolor() {
        ArrayList<StudentDTOcolor> exp = new ArrayList();
        exp.add(new StudentDTOcolor(new Student("abc-123", "Ulrikke Jensen", "www.github.com/ulrikke", "red")));
        exp.add(new StudentDTOcolor(new Student("bcd-234", "Orla Hansen", "www.github.com/orla", "red")));
        exp.add(new StudentDTOcolor(new Student("cde-345", "Werner Bo", "www.github.com/werner", "red")));
        exp.add(new StudentDTOcolor(new Student("def-456", "Gerda Sørensen", "www.github.com/gerda", "red")));
        exp.add(new StudentDTOcolor(new Student("efg-567", "Rigmor Alfsen", "www.github.com/rigmor", "red")));
        assertEquals(exp, facade.getAllStudentDTOcolor());
    }

    @Test
    public void testGetAllStudentDTOcolor_Error() {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Query query = em.createNativeQuery("truncate table CA1_test.STUDENT;");
        query.executeUpdate();
        em.getTransaction().commit();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            facade.getAllStudentDTOcolor();
        });
    }

}
