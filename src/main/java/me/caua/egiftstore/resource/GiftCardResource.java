package me.caua.egiftstore.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.caua.egiftstore.dto.product.GiftCardDTO;
import me.caua.egiftstore.form.ImageForm;
import me.caua.egiftstore.service.GiftCardFileServiceImpl;
import me.caua.egiftstore.service.GiftCardService;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/giftcard")
public class GiftCardResource {

    @Inject
    public GiftCardService giftCardService;
    @Inject
    public GiftCardFileServiceImpl fileService;
    private static final Logger LOG = Logger.getLogger(GiftCardResource.class);

    @PermitAll
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {

        LOG.infof("Executing giftcard search by id: %s", id);

        return Response
                .status(Response.Status.OK)
                .entity(giftCardService.findById(id))
                .build();
    }

    @PermitAll
    @GET
    @Path("/search/name/{name}")
    public Response findByName(@PathParam("name") String name) {

        LOG.infof("Executing giftcard search by name: %s", name);

        return Response
                .status(Response.Status.OK)
                .entity(giftCardService.findByName(name))
                .build();
    }

    @PermitAll
    @GET
    public Response findAll() {

        LOG.infof("Executing find all giftcards");

        return Response
                .status(Response.Status.OK)
                .entity(giftCardService.findAll())
                .build();
    }

    @RolesAllowed({"SALES", "MANAGER", "CEO"})
    @POST
    public Response create(@Valid GiftCardDTO giftCardDTO) {

        LOG.infof("Executing giftcard creation: %s", giftCardDTO.name());
        LOG.debugf("DTO: %s", giftCardDTO);

        return Response
                .status(Response.Status.CREATED)
                .entity(giftCardService.create(giftCardDTO))
                .build();
    }

    @RolesAllowed({"SALES", "MANAGER", "CEO"})
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid GiftCardDTO giftCardDTO) {

        LOG.infof("Executing giftcard update: %s", giftCardDTO.name());
        LOG.debugf("DTO: %s", giftCardDTO);

        giftCardService.update(id, giftCardDTO);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

    @RolesAllowed({"MANAGER", "CEO"})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {

        LOG.infof("Deleting giftcard with id: %s", id);

        giftCardService.delete(id);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

    @RolesAllowed({"SALES", "MANAGER", "CEO"})
    @PATCH
    @Path("/{id}/image/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response saveImage(@PathParam("id") Long id, @MultipartForm ImageForm form) {
        LOG.infof("Saving giftcard (id: %s) image with name: %s", id, form.getImageName());
        fileService.save(id, form.getImageName(), form.getImage());
        return Response.noContent().build();
    }

    @PermitAll
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @GET
    @Path("/image/download/{imageName}")
    public Response download(@PathParam("imageName") String imageName) {
        LOG.infof("Downloading giftcard image with name: %s", imageName);
        Response.ResponseBuilder responseBuilder = Response.ok(fileService.download(imageName));
        responseBuilder.header("Content-Disposition", "attachment;filename="+imageName);
        return responseBuilder.build();
    }

}
