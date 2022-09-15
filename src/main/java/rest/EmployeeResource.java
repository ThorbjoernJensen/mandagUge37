package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Employee;
import entities.dtos.EmployeeDTO;
import facades.EmployeeFacade;
import facades.FacadeExample;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/employee")
public class EmployeeResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final EmployeeFacade FACADE = EmployeeFacade.getFacadeInstance(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String hello() {
        return "\"message\":\"hej Employee\"";
    }

    //    String json = "\"msg\":\"hej\"";
//
//    {"msg":"hej"}
    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAll() {
        List<EmployeeDTO> employeeDTOList = FACADE.getAllEmployees();
        return GSON.toJson(employeeDTOList);
    }


    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getById(@PathParam("id") int id) {
        EmployeeDTO employeeDTO = FACADE.getEmployeeById(id);
        return GSON.toJson(employeeDTO);

    }

    //    highestpaid
    @GET
    @Path("/highestpaid")
    @Produces({MediaType.APPLICATION_JSON})
    public String getHighgestPaid() {
        EmployeeDTO employeeDTO = FACADE.getEmployeesWithHighestSalery().get(0);
        return GSON.toJson(employeeDTO);

    }

    @GET
    @Path("/name/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getByName(@PathParam("name") String name) {
        List<EmployeeDTO> employeeDTOs = FACADE.getEmployeesByName(name);
        return GSON.toJson(employeeDTOs);

    }

}
