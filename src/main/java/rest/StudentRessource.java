package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import entities.Student;
import facades.StudentFacade;
import java.util.ArrayList;
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
    public String data() {
        EntityManager em = EMF.createEntityManager();

        em.getTransaction().begin();
        Query query = em.createNativeQuery("truncate table CA1.STUDENT;");
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
        return "Students created";
    }

    @GET
    @Path("/allstudents")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllStudents() throws Exception {
        try {
            return GSON.toJson(FACADE.getAllStudentDTO());
        } catch (Exception ex) {
            return "{\"error\": \"" + ex.getMessage() + "\"}";
        }
    }

    @GET
    @Path("/{studentid}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getStudentDTOByStudentID(@PathParam("studentid") String studentID) throws Exception {
        try {
            return GSON.toJson(FACADE.getStudentDTOByStudentID(studentID));
        } catch (Exception ex) {
            return "{\"error\": \"" + ex.getMessage() + "\"}";
        }
    }

    @GET
    @Path("/studentbyid/{ids}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getStudentByID(@PathParam("ids") long id) throws Exception {
        try {
            return GSON.toJson(FACADE.getStudentById(id));
        } catch (Exception ex) {
            return "{\"error\": \"" + ex.getMessage() + "\"}";
        }
    }

    @GET
    @Path("/studentbyname/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getStudentDTOByName(@PathParam("name") String name) throws Exception {
        try {
            return GSON.toJson(FACADE.getStudentDTOByName(name));
        } catch (Exception ex) {
            return "{\"error\": \"" + ex.getMessage() + "\"}";
        }
    }
}
