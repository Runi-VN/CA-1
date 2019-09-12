package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    @Produces({MediaType.APPLICATION_JSON})
    public String data() {
        EntityManager em = EMF.createEntityManager();

        em.getTransaction().begin();
        Query query = em.createNativeQuery("truncate table CA1.STUDENT;");
        query.executeUpdate();
        em.getTransaction().commit();

        ArrayList<Student> allStudents = new ArrayList();
        allStudents.add(new Student("cs340", "Camilla Staunstrup", "https://github.com/Castau"));
        allStudents.add(new Student("mh748", "Malte Hviid-Magnussen", "https://github.com/MalteMagnussen"));
        allStudents.add(new Student("ab363", "Asger Bjarup", "https://github.com/HrBjarup"));
        allStudents.add(new Student("rn118", "Rúni Niclassen", "https://github.com/Runi-VN"));
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
            return GSON.toJson(FACADE.getAllStudentDTO());
        } catch (Exception ex) {
            return "{\"error\": \"" + ex.getMessage() + "\"}";
        }
    }

    @GET
    @Path("/studentid/{studentid}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getStudentDTOByStudentID(@PathParam("studentid") String studentID) throws Exception {
        try {
            return GSON.toJson(FACADE.getStudentDTOByStudentID(studentID));
        } catch (Exception ex) {
            return "{\"error\": \"" + ex.getMessage() + "\"}";
        }
    }

    @GET
    @Path("/databaseid/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getStudentByDatabaseID(@PathParam("id") long id) throws Exception {
        try {
            return GSON.toJson(FACADE.getStudentByDatabaseId(id));
        } catch (Exception ex) {
            return "{\"error\": \"" + ex.getMessage() + "\"}";
        }
    }

    @GET
    @Path("/studentname/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getStudentDTOByName(@PathParam("name") String name) throws Exception {
        try {
            return GSON.toJson(FACADE.getStudentDTOByName(name));
        } catch (Exception ex) {
            return "{\"error\": \"" + ex.getMessage() + "\"}";
        }
    }
}
