package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CarDTO;
import entities.Car;
import facades.CarFacade;
import utils.EMF_Creator;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("car")
public class CarResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV,EMF_Creator.Strategy.CREATE);
    private static final CarFacade FACADE =  CarFacade.getCarFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"We're selling cars!\"}";
    }
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getCarCount() {
        long count = FACADE.getCarCount();
        return "{\"count\":\"There are "+count+" cars for sale\"}";
    }
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String allCars()
    {
        List<CarDTO> all = FACADE.getAllCars();
        if (all != null)
        {
            return GSON.toJson(all);
        } else if (all.isEmpty())
        {
            return "{\"msg\":\"There are currently no cars for sale!\"}";
        } else
        {
            return "{\"msg\":\"Failed to fetch all cars!\"}";
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String carByID(@PathParam("id") Long id)
    {
        CarDTO carWithID = FACADE.findCarByID(id);
        if (carWithID != null)
        {
            return GSON.toJson(carWithID);
        } else
        {
            return "{\"msg\":\"The car doesn't exist or we failed to retrieve it!\"}";
        }
    }
    
    @GET
    @Path("/{id}/allInfo")
    @Produces(MediaType.APPLICATION_JSON)
    public String carByIDDetailed(@PathParam("id") Long id)
    {
        Car carWithID = FACADE.findCarByIDAllInfo(id);
        if (carWithID != null)
        {
            return GSON.toJson(carWithID);
        } else
        {
            return "{\"msg\":\"The car doesn't exist or we failed to retrieve it!\"}";
        }
    }
}
