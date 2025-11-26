package me.caua.egiftstore.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException e) {

        ValidationError validationError = new ValidationError("400", "Erro de validação");
        validationError.addFieldError(e.getFieldName(), e.getMessage());

        return Response.status(Response.Status.BAD_REQUEST).entity(validationError).build();
    }
}
