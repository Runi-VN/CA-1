package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import utils.EMF_Creator;
import facades.JokeFacade;
import javax.persistence.EntityManagerFactory;
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
