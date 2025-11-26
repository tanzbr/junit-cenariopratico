package me.caua.egiftstore.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.caua.egiftstore.dto.product.GiftCompanyDTO;
import me.caua.egiftstore.form.ImageForm;
import me.caua.egiftstore.service.GiftCompanyFileServiceImpl;
import me.caua.egiftstore.service.GiftCompanyService;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/giftcompany")
public class GiftCompanyResource {

    @Inject
    public GiftCompanyService giftCompanyService;
    @Inject
    public GiftCompanyFileServiceImpl fileService;
    private static final Logger LOG = Logger.getLogger(GiftCompanyResource.class);

    @PermitAll
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {

        LOG.infof("Executing giftcompany search by id: %s", id);

        return Response
                .status(Response.Status.OK)
                .entity(giftCompanyService.findById(id))
                .build();
    }

    @PermitAll
    @GET
    @Path("/search/name/{name}")
    public Response findByName(@PathParam("name") String name) {

        LOG.infof("Executing giftcompany search by name: %s", name);

        return Response
                .status(Response.Status.OK)
                .entity(giftCompanyService.findByName(name))
                .build();
    }

    @PermitAll
    @GET
    public Response findAll() {

        LOG.infof("Executing find all giftcompanies");

        return Response
                .status(Response.Status.OK)
                .entity(giftCompanyService.findAll())
                .build();
    }

    @RolesAllowed({"SALES", "MANAGER", "CEO"})
    @POST
    public Response create(@Valid GiftCompanyDTO giftCompanyDTO) {

        LOG.infof("Executing giftcompany creation: %s", giftCompanyDTO.name()+" - "+giftCompanyDTO.cnpj());
        LOG.debugf("DTO: %s", giftCompanyDTO);

        return Response
                .status(Response.Status.CREATED)
                .entity(giftCompanyService.create(giftCompanyDTO))
                .build();
    }

    @RolesAllowed({"SALES", "MANAGER", "CEO"})
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid GiftCompanyDTO giftCompanyDTO) {

        LOG.infof("Executing giftcompany update: %s", giftCompanyDTO.name()+" - "+giftCompanyDTO.cnpj());
        LOG.debugf("DTO: %s", giftCompanyDTO);

        giftCompanyService.update(id, giftCompanyDTO);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

    @RolesAllowed({"SALES", "MANAGER", "CEO"})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {

        LOG.infof("Deleting giftcompany with id: %s", id);

        giftCompanyService.delete(id);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

    @RolesAllowed({"SALES", "MANAGER", "CEO"})
    @PATCH
    @Path("/{id}/image/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response saveImage(@PathParam("id") Long id, @MultipartForm ImageForm form) {
        LOG.infof("Saving image for giftcompany (id: %s) with name: %s", id, form.getImageName());
        fileService.save(id, form.getImageName(), form.getImage());
        return Response.noContent().build();
    }

    @PermitAll
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @GET
    @Path("/image/download/{imageName}")
    public Response download(@PathParam("imageName") String imageName) {
        LOG.infof("Downloading image of giftcompany with name: %s", imageName);
        Response.ResponseBuilder responseBuilder = Response.ok(fileService.download(imageName));
        responseBuilder.header("Content-Disposition", "attachment;filename="+imageName);
        return responseBuilder.build();
    }

}
