package me.caua.egiftstore.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.caua.egiftstore.dto.auth.AuthUserDTO;
import me.caua.egiftstore.dto.user.UserResponseDTO;
import me.caua.egiftstore.service.CustomerService;
import me.caua.egiftstore.service.EmployeeService;
import me.caua.egiftstore.service.HashService;
import me.caua.egiftstore.service.JwtService;
import me.caua.egiftstore.validation.BeanValidationExceptionMapper;
import me.caua.egiftstore.validation.ValidationException;
import org.jboss.logging.Logger;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/auth")
public class AuthResource {

    @Inject
    public HashService hashService;
    @Inject
    public EmployeeService employeeService;
    @Inject
    public CustomerService customerService;
    @Inject
    public JwtService jwtService;
    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @POST
    public Response create(AuthUserDTO authUserDTO) {

        LOG.infof("Executing user authentication: %s", authUserDTO.email());
        LOG.debugf("DTO: %s", authUserDTO);

        String hash = hashService.getHashSenha(authUserDTO.password());
        UserResponseDTO user = null;
        int type = authUserDTO.profile();

        // 0 = employee
        if (type == 0) {
            user = employeeService.login(authUserDTO.email(), hash);
            // 1 = customer
        } else if (type == 1) {
            user = customerService.login(authUserDTO.email(), hash);
        } else {
            throw new ValidationException("profile", "profile must be 0 (employee) or 1 (customer)");
        }

        return Response
                .ok(user)
                .header("Authorization", jwtService.generateJwt(user, type))
                .build();
    }

}
