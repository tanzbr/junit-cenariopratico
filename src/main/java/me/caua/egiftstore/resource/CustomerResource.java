package me.caua.egiftstore.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.caua.egiftstore.dto.user.CustomerDTO;
import me.caua.egiftstore.service.CustomerService;
import me.caua.egiftstore.validation.BeanValidationExceptionMapper;
import org.jboss.logging.Logger;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/customer")
public class CustomerResource {

    @Inject
    public CustomerService customerService;
    private static final Logger LOG = Logger.getLogger(CustomerResource.class);

    @PermitAll
    @POST
    public Response create(@Valid CustomerDTO customerDTO) {

        LOG.infof("Executing customer creation: %s", customerDTO.userDTO().email());
        LOG.debugf("DTO: %s", customerDTO);

        return Response
                .status(Response.Status.CREATED)
                .entity(customerService.create(customerDTO))
                .build();
    }

    @RolesAllowed({"MANAGER", "CEO"})
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {

        LOG.infof("Executing user search by id: %s", id);

        return Response
                .status(Response.Status.OK)
                .entity(customerService.findById(id))
                .build();
    }

    @RolesAllowed({"MANAGER", "CEO"})
    @GET
    @Path("/search/name/{name}")
    public Response findByName(@PathParam("name") String name) {

        LOG.infof("Executing user search by name: %s", name);

        return Response
                .status(Response.Status.OK)
                .entity(customerService.findByName(name))
                .build();
    }

    @RolesAllowed({"MANAGER", "CEO"})
    @GET
    public Response findAll() {

        LOG.infof("Executing find all users");

        return Response
                .status(Response.Status.OK)
                .entity(customerService.findAll())
                .build();
    }

    @RolesAllowed({"MANAGER", "CEO"})
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid CustomerDTO customerDTO) {

        LOG.infof("Updating user with id: %s", id);
        LOG.debugf("DTO: %s", customerDTO);

        customerService.update(id, customerDTO);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

    @RolesAllowed({"MANAGER", "CEO"})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {

        LOG.infof("Deleting user with id: %s", id);

        customerService.delete(id);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

}
