package facades;

import dto.StudentDTO;
import dto.StudentDTOcolor;
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

    public Student getStudentByDatabaseId(long id) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Student student = em.find(Student.class, id);
            if(student == null){
                throw new IllegalArgumentException();
            }
            return student;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find student");
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
            if(result == null || result.isEmpty()){
                throw new IllegalArgumentException();
            }
            return result;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find student");
        } finally {
            em.close();
        }
    }

    public StudentDTO getStudentDTOByStudentID(String studentID) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Student student = em.createNamedQuery("Student.getByStudentID", Student.class).setParameter("studentID", studentID).getSingleResult();
            if(student == null){
                throw new IllegalArgumentException();
            }
            return new StudentDTO(student);
            } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find student");
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
            if(result == null || result.isEmpty()){
                throw new IllegalArgumentException();
            }
            return result;
            } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find students: " + ex.getMessage());
        } finally {
            em.close();
        }
    }
    
    public StudentDTOcolor getStudentDTOcolorByStudentID(String studentID) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Student student = em.createNamedQuery("Student.getByStudentID", Student.class).setParameter("studentID", studentID).getSingleResult();
            return new StudentDTOcolor(student);
            } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find student: " + ex.getMessage());
        } finally {
            em.close();
        }
    }
    
    public List<StudentDTOcolor> getAllStudentDTOcolor() {
        EntityManager em = getEntityManager();
        try {
            List<Student> students = em.createNamedQuery("Student.getAll").getResultList();
            List<StudentDTOcolor> result = new ArrayList<>();
            students.forEach((student) -> {
                result.add(new StudentDTOcolor(student));
            });
            if(result == null || result.isEmpty()){
                throw new IllegalArgumentException();
            }
            return result;
            } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find students: " + ex.getMessage());
        } finally {
            em.close();
        }
    }
}
