package facades;

import entities.Joke;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class JokeFacade {

    private static JokeFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private JokeFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static JokeFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new JokeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getJokeCount() {
        EntityManager em = getEntityManager();
        try {
            return (long) em.createQuery("SELECT COUNT(r) FROM Joke r").getSingleResult();
        } catch (Exception ex) {
            //em.getTransaction().rollback();
            throw new IllegalArgumentException("Could not get joke count");
        } finally {
            em.close();
        }

    }

    public List<Joke> getAllJokes() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT r FROM Joke r", Joke.class).getResultList();
        } catch (Exception ex) {
            //em.getTransaction().rollback();
            throw new IllegalArgumentException("Could not get all jokes" + ex.getMessage());
        } finally {
            em.close();
        }
    }

    public List<Joke> getAllJokesAsDTO() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT NEW dto.JokeDTO(j) FROM Joke j", Joke.class).getResultList();
        } catch (Exception ex) {
            //em.getTransaction().rollback();
            throw new IllegalArgumentException("Could not get all jokes (DTO)" + ex.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * em.find doesn't actually throw an exception, it just returns null...
     * @param id
     * @return 
     */
    public Joke getJokeById(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Joke.class, id);
        } catch (Exception ex) {
            //em.getTransaction().rollback();
            throw new IllegalArgumentException("Could not get joke by ID" + ex.getMessage());
        } finally {
            em.close();
        }
    }

}
