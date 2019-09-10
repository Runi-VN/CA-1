package facades;

import dto.WhoDidWhatDTO;
import entities.WhoDidWhat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class WhoDidWhatFacade {

    private static WhoDidWhatFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private WhoDidWhatFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static WhoDidWhatFacade getWhoDidWhatFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new WhoDidWhatFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Get Work Done by Name
     *
     * @param name of the student. Either Camilla, Malte, Asger or Runì
     * @return WhoDidWhatDTO
     * @throws Exception
     */
    public WhoDidWhatDTO getWorkDoneByName(String name) throws IllegalArgumentException {
        EntityManager em = getEntityManager();
        try {
            return new WhoDidWhatDTO(em.createNamedQuery("WhoDidWhat.getByName", WhoDidWhat.class).setParameter("name", name).getSingleResult());
        } catch (Exception e) {
            throw new IllegalArgumentException("No Student found by that name.");
        } finally {
            em.close();
        }
    }

    public List<WhoDidWhatDTO> getAllWorkDone() {
        EntityManager em = getEntityManager();
        try {
            List<WhoDidWhat> whodidwhat = em.createNamedQuery("WhoDidWhat.getAll").getResultList();
            List<WhoDidWhatDTO> result = new ArrayList<>();
            whodidwhat.forEach((who) -> {
                result.add(new WhoDidWhatDTO(who));
            });
            return result;
        } finally {
            em.close();
        }
    }

    public WhoDidWhat makeWork(String name, String work) {
        EntityManager em = getEntityManager();
        try {
            try {
                WhoDidWhat student = em.createNamedQuery("WhoDidWhat.getByName", WhoDidWhat.class).setParameter("name", name).getSingleResult();
                em.getTransaction().begin();
                student.addDone(work);
                em.getTransaction().commit();
                return student;
            } catch (Exception e) {
                WhoDidWhat student = new WhoDidWhat(name);
                student.addDone(work);
                em.getTransaction().begin();
                em.persist(student);
                em.getTransaction().commit();
                return student;
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new IllegalArgumentException("Wrong input when inputting work or name.");
        } finally {
            em.close();
        }
    }

}
