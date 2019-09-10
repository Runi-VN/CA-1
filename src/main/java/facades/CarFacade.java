package facades;

import dto.CarDTO;
import entities.Car;
import entities.RenameMe;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class CarFacade {

    private static CarFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private CarFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CarFacade getCarFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CarFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    /**
     * Used to get number of cars currently in the database.
     * @return long
     */
    public long getCarCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long CarCount = (long)em.createQuery("SELECT COUNT(c) FROM Car c").getSingleResult();
            return CarCount;
        }finally{  
            em.close();
        }
        
    }

    /**
     * Used to get a list of all cars.
     * The list will contain CarDTOs
     * @return List of CarDTOs. Returns null if the method fails
     */
    public List<CarDTO> getAllCars()
    {
        EntityManager em = getEntityManager();
        try
        {
            TypedQuery<CarDTO> query
                    = em.createQuery("SELECT new dto.CarDTO(c) FROM Car c", CarDTO.class);
            return query.getResultList();
        } catch (Exception ex)
        {
            System.out.println("Failed to retrieve all cars");
            ex.printStackTrace();
            return null;
        } finally
        {
            em.close();
        }
    }
    
    /**
     * Used to persist a car object in the database.
     * This method is meant to be used only by admins.
     * @param car
     * @return Car object with it's new ID. Returns null if the method fails.
     */
    public Car addCar(Car car)
    {
        EntityManager em = getEntityManager();
        try
        {
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();
            return car;
        } catch (Exception ex)
        {
            System.out.println("Failed to persist object");
            ex.printStackTrace();
            return null;
        } finally
        {
            em.close();
        }
    }
    
    /**
     * Used to find a car based on the given ID. 
     * This method returns a car object with all it's information. This method 
     * is meant to be used only by admins.
     * @param id
     * @return Car object. Returns null if the method fails or if there is no 
     * matching ID in the database.
     */
    public Car findCarByIDAllInfo(Long id)
    {
        EntityManager em = getEntityManager();
        try
        {
            Car car = em.find(Car.class, id);
            return car;
        } catch (Exception ex)
        {
            System.out.println("Failed to search for the car");
            ex.printStackTrace();
            return null;
        } finally
        {
            em.close();
        }
    }
    
    /**
     * Used to find a car based on the given ID.
     * @param id
     * @return CarDTO. Returns null if the search fails or if there is no 
     * matching ID in the database.
     */
    public CarDTO findCarByID(Long id)
    {
        EntityManager em = getEntityManager();
        try
        {
            Car car = em.find(Car.class, id);
            CarDTO cDTO = new CarDTO(car);
            return cDTO;
        } catch (Exception ex)
        {
            System.out.println("Failed to search for the car");
            ex.printStackTrace();
            return null;
        } finally
        {
            em.close();
        }
    }
}
