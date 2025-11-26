package me.caua.egiftstore.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import me.caua.egiftstore.dto.other.EmailDTO;
import me.caua.egiftstore.dto.other.StringDTO;
import me.caua.egiftstore.dto.user.CustomerDTO;
import me.caua.egiftstore.repository.UserRepository;
import me.caua.egiftstore.service.UserService;
import org.jboss.logging.Logger;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/user")
public class UserResource {

    @Inject
    UserService userService;
    private static final Logger LOG = Logger.getLogger(GiftCodeResource.class);

    @Path("/update/name")
    @RolesAllowed({"CUSTOMER", "SALES", "MANAGER", "CEO"})
    @PATCH()
    public Response updateName(@Valid StringDTO stringDTO, @Context SecurityContext securityContext) {

        LOG.infof("Updating name of user with email %s. New name: %s", securityContext.getUserPrincipal().getName(), stringDTO.content());

        userService.updateName(stringDTO, securityContext);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

    @Path("/update/email")
    @RolesAllowed({"CUSTOMER", "SALES", "MANAGER", "CEO"})
    @PATCH()
    public Response updateEmail(@Valid EmailDTO emailDTO, @Context SecurityContext securityContext) {
        LOG.infof("Updating email of user with email %s. New email: %s", securityContext.getUserPrincipal().getName(), emailDTO.content());
        userService.updateEmail(emailDTO, securityContext);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

    @Path("/update/password")
    @RolesAllowed({"CUSTOMER", "SALES", "MANAGER", "CEO"})
    @PATCH()
    public Response updatePass(@Valid StringDTO stringDTO, @Context SecurityContext securityContext) {
        LOG.infof("Updating password of user with email %s.", securityContext.getUserPrincipal().getName());
        userService.updatePass(stringDTO, securityContext);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

}
