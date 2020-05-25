package com.fmoraes.plangenerator.resources;

import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author fmoraes
 */
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        
        var errors = e.getConstraintViolations()
                .stream()
                .filter(violation -> violation.getMessage() != null)
                .collect(
                        Collectors.groupingBy(violation -> violation.getPropertyPath().toString(),
                                Collectors.mapping(violation -> violation.getMessage(), Collectors.toSet())
                        )
                );
        
        System.out.println(errors);
        
        return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
    }

}
