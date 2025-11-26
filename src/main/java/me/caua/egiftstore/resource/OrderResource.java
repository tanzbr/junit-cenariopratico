package me.caua.egiftstore.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import me.caua.egiftstore.dto.order.OrderDTO;
import me.caua.egiftstore.service.OrderService;
import org.jboss.logging.Logger;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/order")
public class OrderResource {

    @Inject
    public OrderService orderService;
    private static final Logger LOG = Logger.getLogger(OrderResource.class);

    @RolesAllowed({"SALES", "MANAGER", "CEO"})
    @GET
    public Response findAll() {

        LOG.infof("Executing find all orders");

        return Response
                .status(Response.Status.OK)
                .entity(orderService.findAll())
                .build();
    }

    @RolesAllowed({"CUSTOMER", "SALES", "MANAGER", "CEO"})
    @POST
    public Response create(@Valid OrderDTO orderDTO) {

        LOG.infof("Executing order creation for user: %s", orderDTO.customerId());
        LOG.debugf("DTO: %s", orderDTO);

        return Response
                .status(Response.Status.CREATED)
                .entity(orderService.create(orderDTO))
                .build();
    }

    @RolesAllowed({"SALES", "MANAGER", "CEO"})
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {

        LOG.infof("Executing order search by id: %s", id);

        return Response
                .status(Response.Status.OK)
                .entity(orderService.findById(id))
                .build();
    }

    @RolesAllowed({"CUSTOMER", "SALES", "MANAGER", "CEO"})
    @GET
    @Path("/findByMe")
    public Response findByCustomerEmail(@Context SecurityContext securityContext) {

        LOG.infof("Executing order search by customer email");

        return Response
                .status(Response.Status.OK)
                .entity(orderService.findByCustomerEmail(securityContext))
                .build();
    }

    @RolesAllowed({"SALES", "MANAGER", "CEO"})
    @GET
    @Path("/customer/{id}")
    public Response findByCustomer(@PathParam("id") Long id) {

        LOG.infof("Executing order search by customer: %s", id);

        return Response
                .status(Response.Status.OK)
                .entity(orderService.findByCustomerId(id))
                .build();
    }

}
