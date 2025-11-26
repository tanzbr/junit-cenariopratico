package me.caua.egiftstore.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.caua.egiftstore.dto.payment.PaymentDTO;
import me.caua.egiftstore.service.PaymentService;
import org.jboss.logging.Logger;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/payment")
public class PaymentResource {

    @Inject
    public PaymentService paymentService;
    private static final Logger LOG = Logger.getLogger(GiftCodeResource.class);

    @RolesAllowed({"SALES", "MANAGER", "CEO"})
    @GET
    public Response findAll() {

        LOG.infof("Executing find all payments");

        return Response
                .status(Response.Status.OK)
                .entity(paymentService.findAll())
                .build();
    }

    @RolesAllowed({"SALES", "MANAGER", "CEO"})
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid PaymentDTO paymentDTO) {

        LOG.infof("Executing payment update: %s", id);
        LOG.debugf("DTO: %s", paymentDTO);

        paymentService.update(id, paymentDTO);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

    @RolesAllowed({"SALES", "MANAGER", "CEO"})
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {

        LOG.infof("Executing payment search by id: %s", id);

        return Response
                .status(Response.Status.OK)
                .entity(paymentService.findById(id))
                .build();
    }

}
