package me.caua.egiftstore.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.caua.egiftstore.dto.user.EmployeeDTO;
import me.caua.egiftstore.service.EmployeeService;
import org.jboss.logging.Logger;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/employee")
public class EmployeeResource {

    @Inject
    public EmployeeService employeeService;
    private static final Logger LOG = Logger.getLogger(EmployeeResource.class);

    @RolesAllowed({"MANAGER", "CEO"})
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {

        LOG.infof("Executing user search by id: %s", id);

        return Response
                .status(Response.Status.OK)
                .entity(employeeService.findById(id))
                .build();
    }

    @RolesAllowed({"MANAGER", "CEO"})
    @GET
    @Path("/search/name/{name}")
    public Response findByName(@PathParam("name") String name) {

        LOG.infof("Executing user search by name: %s", name);

        return Response
                .status(Response.Status.OK)
                .entity(employeeService.findByName(name))
                .build();
    }

    @RolesAllowed({"MANAGER", "CEO"})
    @GET
    @Path("/search/cpf/{cpf}")
    public Response findByCpf(@PathParam("cpf") String cpf) {

        LOG.infof("Executing user search by cpf: %s", cpf);

        return Response
                .status(Response.Status.OK)
                .entity(employeeService.findByCpf(cpf))
                .build();
    }

    @RolesAllowed({"MANAGER", "CEO"})
    @GET
    public Response findAll() {

        LOG.infof("Executing find all users");

        return Response
                .status(Response.Status.OK)
                .entity(employeeService.findAll())
                .build();
    }

    @POST
    public Response create(@Valid EmployeeDTO employeeDTO) {

        LOG.infof("Executing employee creation: %s", employeeDTO.user().email());
        LOG.debugf("DTO: %s", employeeDTO);

        return Response
                .status(Response.Status.CREATED)
                .entity(employeeService.create(employeeDTO))
                .build();
    }

    @RolesAllowed({"MANAGER", "CEO"})
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid EmployeeDTO employeeDTO) {

        LOG.infof("Executing employee update: %s", employeeDTO.user().email());
        LOG.debugf("DTO: %s", employeeDTO);

        employeeService.update(id, employeeDTO);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

    @RolesAllowed({"CEO"})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {

        LOG.infof("Deleting user with id: %s", id);

        employeeService.delete(id);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

}
