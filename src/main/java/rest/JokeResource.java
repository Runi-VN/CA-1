package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import entities.Joke;
import utils.EMF_Creator;
import facades.JokeFacade;
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

//Todo Remove or change relevant parts before ACTUAL use
@Path("jokes")
public class JokeResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.DROP_AND_CREATE);
    private static final JokeFacade FACADE = JokeFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        List<Joke> jokes;
        EntityManager em = EMF.createEntityManager();
        jokes = new ArrayList(); //init

        //add to collection
        jokes.add(new Joke("A programmer puts two glasses on his bedside table before going to sleep. A full one, in case he gets thirsty, and an empty one, in case he doesn’t.", "https://redd.it/1kvhmz", "case-handling", 10));
        jokes.add(new Joke("A programmer is heading out to the grocery store, so his wife tells him \"get a gallon of milk, and if they have eggs, get a dozen.\" He returns with 13 gallons of milk.", "https://redd.it/1kvhmz", "numbers", 9));
        jokes.add(new Joke("What do programmers do before sex? Initialize <code>for</code>-play.", "https://redd.it/1kvhmz", "naughty", 7));
        jokes.add(new Joke("A programmer heads out to the store. His wife says \"while you're out, get some milk.\"", "https://redd.it/1kvhmz", "loops", 5));

        try {
            em.getTransaction().begin();
            Query query = em.createNativeQuery("truncate table CA1.JOKE;");
            query.executeUpdate();
            em.getTransaction().commit();
            for (Joke j : jokes) {
                em.getTransaction().begin();
                em.persist(j);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
        
        
        return "{\"msg\":\"ACCESS GRANTED\"}";
    }
    
    @Path("/count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getJokeCount() {
        try {
            JsonObject count = new JsonObject();
            count.addProperty("count", FACADE.getJokeCount());
            return GSON.toJson(count);
        } catch (Exception ex) {
            return "{\"error\": \"" + ex.getMessage() + "\"}";
        }
    }

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllJokes() throws Exception {
        try {
            return GSON.toJson(FACADE.getAllJokes());
        } catch (Exception ex) {
            return "{\"error\": \"" + ex.getMessage() + "\"}";
        }
    }
    
     @GET
    @Path("/all/dto")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllJokesAsDTO() throws Exception {
        try {
            return GSON.toJson(FACADE.getAllJokesAsDTO());
        } catch (Exception ex) {
            return "{\"error\": \"" + ex.getMessage() + "\"}";
        }
    }


    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getJokeByID(@PathParam("id") Long id) throws Exception {
        try {
            return GSON.toJson(FACADE.getJokeById(id));
        } catch (Exception ex) {
            return "{\"error\": \"" + ex.getMessage() + "\"}";
        }
    }

    @GET
    @Path("/{id}/dto")
    @Produces({MediaType.APPLICATION_JSON})
    public String getJokeDTOByID(@PathParam("id") Long id) throws Exception {
        try {
            return GSON.toJson(FACADE.getJokeByIdAsDTO(id));
        } catch (Exception ex) {
            return "{\"error\": \"" + ex.getMessage() + "\"}";
        }
    }
    
    @GET
    @Path("/random")
    @Produces({MediaType.APPLICATION_JSON})
    public String getJokeRandom() throws Exception {
        try {
            return GSON.toJson(FACADE.getJokeByRandom());
        } catch (Exception ex) {
            return "{\"error\": \"" + ex.getMessage() + "\"}";
        }
    }
    
    @GET
    @Path("/random/dto")
    @Produces({MediaType.APPLICATION_JSON})
    public String getJokeRandomAsDTO() throws Exception {
        try {
            return GSON.toJson(FACADE.getJokeByRandomAsDTO());
        } catch (Exception ex) {
            return "[\"error\": \"" + ex.getMessage() + "\"}";
        }
    }

}
