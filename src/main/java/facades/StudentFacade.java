package facades;

import dto.StudentDTO;
import entities.Student;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Camilla
 */
public class StudentFacade {

    private static StudentFacade instance;
    private static EntityManagerFactory emf;

    private StudentFacade() {
    }

    public static StudentFacade getStudentFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new StudentFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public StudentDTO getStudentById(long id) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Student student = em.find(Student.class, id);
            return new StudentDTO(student);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find student: " + ex.getMessage());
        } finally {

            em.close();
        }
    }

    public List<StudentDTO> getStudentDTOByName(String name) throws Exception {
        EntityManager em = getEntityManager();
        try {
            List<Student> students = em.createNamedQuery("Student.getByName").setParameter("name", name).getResultList();
            List<StudentDTO> result = new ArrayList<>();
            students.forEach((student) -> {
                result.add(new StudentDTO(student));
            });
            return result;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find student: " + ex.getMessage());
        } finally {
            em.close();
        }
    }

    public StudentDTO getStudentDTOByStudentID(String studentID) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Student student = em.createNamedQuery("Student.getByStudentID", Student.class).setParameter("studentID", studentID).getSingleResult();
            return new StudentDTO(student);
            } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find student: " + ex.getMessage());
        } finally {
            em.close();
        }
    }

    public List<StudentDTO> getAllStudentDTO() {
        EntityManager em = getEntityManager();
        try {
            List<Student> students = em.createNamedQuery("Student.getAll").getResultList();
            List<StudentDTO> result = new ArrayList<>();
            students.forEach((student) -> {
                result.add(new StudentDTO(student));
            });
            return result;
            } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find students: " + ex.getMessage());
        } finally {
            em.close();
        }
    }
}
