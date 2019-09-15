package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.StudentDTO;
import dto.StudentDTOcolor;
import entities.Student;
import facades.StudentFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 *
 * @author Camilla
 */
@Path("students")
public class StudentRessource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final StudentFacade FACADE = StudentFacade.getStudentFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/data")
    @Produces({MediaType.APPLICATION_JSON})
    public String data() {
        EntityManager em = EMF.createEntityManager();

        em.getTransaction().begin();
        Query query = em.createNativeQuery("truncate table CA1.STUDENT;");
        query.executeUpdate();
        em.getTransaction().commit();

        ArrayList<Student> allStudents = new ArrayList();
        allStudents.add(new Student("cs340", "Camilla Staunstrup", "github.com/Castau", "red"));
        allStudents.add(new Student("mh748", "Malte Hviid-Magnussen", "github.com/MalteMagnussen", "red"));
        allStudents.add(new Student("ab363", "Asger Bjarup", "github.com/HrBjarup", "red"));
        allStudents.add(new Student("rn118", "Rúni Niclassen", "github.com/Runi-VN", "red"));
        try {
            for (Student s : allStudents) {
                em.getTransaction().begin();
                em.persist(s);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            return "{\"dataMsg\":\"An error occured\"}";
        } finally {
            em.close();
        }
        return "{\"dataMsg\":\"Students created\"}";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"path students succesful\"}";
    }

    @GET
    @Path("/allstudents")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllStudents() throws Exception {
        try {
            List<StudentDTO> allstudents = FACADE.getAllStudentDTO();
            if (allstudents == null || allstudents.isEmpty()) {
                return "{\"error\":\"no students exists in the database\"}";
            } else {
                return GSON.toJson(allstudents);
            }
        } catch (Exception ex) {
            return "{\"error\":\"database error\"}";
        }
    }

    @GET
    @Path("/allstudentscolor")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllStudentsColor() throws Exception {
        try {
            List<StudentDTOcolor> allstudentscolor = FACADE.getAllStudentDTOcolor();
            if (allstudentscolor == null || allstudentscolor.isEmpty()) {
                return "{\"error\":\"no students exists in the database\"}";
            } else {
                return GSON.toJson(allstudentscolor);
            }
        } catch (Exception ex) {
            return "{\"error\":\"database error\"}";
        }
    }

    @GET
    @Path("/studentid/{studentid}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getStudentDTOByStudentID(@PathParam("studentid") String studentID) throws Exception {
        try {
            return GSON.toJson(FACADE.getStudentDTOcolorByStudentID(studentID));
        } catch (Exception ex) {
            return "{\"error\":\"no student by that id\"}";
        }
    }

    @GET
    @Path("/databaseid/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getStudentByDatabaseID(@PathParam("id") long id) throws Exception {
        try {
            Student student = FACADE.getStudentByDatabaseId(id);
            if (student == null) {
                return "{\"error\":\"no student by that id\"}";
            } else {
                return GSON.toJson(student);
            }
        } catch (Exception ex) {
            return "{\"error\":\"no student by that id\"}";
        }
    }

    @GET
    @Path("/studentname/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getStudentDTOByName(@PathParam("name") String name) throws Exception {
        try {
            List<StudentDTO> studentname = FACADE.getStudentDTOByName(name);
            if (studentname == null || studentname.isEmpty()) {
                return "{\"error\":\"no students by that name\"}";
            } else {
                return GSON.toJson(FACADE.getStudentDTOByName(name));
            }
        } catch (Exception ex) {
            return "{\"error\":\"database error\"}";
        }
    }
}
