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
     * @param name of the student. Either Camilla, Malte, Asger or Runì
     * @return WhoDidWhatDTO
     * @throws Exception 
     */
    public WhoDidWhatDTO getWorkDoneByName(String name) throws Exception {
        EntityManager em = getEntityManager();
        try {
            return new WhoDidWhatDTO(em.createNamedQuery("WhoDidWhat.getByName", WhoDidWhat.class).setParameter("name", name).getSingleResult());
        } catch (Exception e) {
            throw new Exception("No Student found by that name.");
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

//
//    /**
//     * Get Ticket Sales for a Movie by ID. To be used internally only.
//     *
//     * @param id of the Movie.
//     * @return Number of Ticket sales as a Long.
//     */
//    public Long getTicketSales(int id) {
//        EntityManager em = getEntityManager();
//        try {
//            return (Long) em.createQuery("SELECT r.ticketsSold FROM Movie r WHERE r.id = :id").setParameter("id", id).getSingleResult();
//        } finally {
//            em.close();
//        }
//    }
//
//    /**
//     * Get all Movies in DTO format.
//     *
//     * @return
//     */
//    public List<MovieDTO> getAllMoviesDTO() {
//        EntityManager em = getEntityManager();
//        try {
//            List<Movie> movies = em.createNamedQuery("Movie.findAll").getResultList();
//            List<MovieDTO> result = new ArrayList<>();
//            movies.forEach((movie) -> {
//                result.add(new MovieDTO(movie));
//            });
//            return result;
//        } finally {
//            em.close();
//        }
//    }
//
//    /**
//     * Get MovieDTO by ID.
//     *
//     * @param id of the Movie
//     * @return MovieDTO
//     */
//    public MovieDTO getMovieDTOById(Long id) {
//        EntityManager em = getEntityManager();
//        try {
//            Movie movie = em.find(Movie.class, id);
//            return new MovieDTO(movie);
//        } finally {
//            em.close();
//        }
//
//    }
//
//    public Movie makeMovie(Movie movie) {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.persist(movie);
//            em.getTransaction().commit();
//            return movie;
//        } catch (Exception e) {
//            em.getTransaction().rollback();
//        } finally {
//            em.close();
//        }
//        return null;
//    }
//
//    /**
//     * Get number of movies in the database.
//     *
//     * @return
//     */
//    public Long getMovieCount() {
//        EntityManager em = getEntityManager();
//        try {
//            Long result = (long) em.createNamedQuery("Movie.count").getSingleResult();
//            return result;
//        } finally {
//            em.close();
//        }
//    }
//
//    /**
//     * Returns MovieDTO by name.
//     *
//     * @param name
//     * @return
//     * @throws java.lang.Exception
//     */
//    public MovieDTO getMovieDTOByName(String name) throws Exception {
//        EntityManager em = getEntityManager();
//        try {
//            return em.createQuery("SELECT new dto.MovieDTO(m) FROM Movie m WHERE m.name = :name", MovieDTO.class).setParameter("name", name).getSingleResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception("No customer found by that name");
//        } finally {
//            em.close();
//        }
//    }

    
}
