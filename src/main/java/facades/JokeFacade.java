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
            throw new IllegalArgumentException("Could not get all movies" + ex.getMessage());
        } finally {            
            em.close();
        }
    }
    
}
