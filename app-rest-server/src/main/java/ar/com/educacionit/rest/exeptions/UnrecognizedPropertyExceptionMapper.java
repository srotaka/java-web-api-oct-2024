package ar.com.educacionit.rest.exeptions;


import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnrecognizedPropertyExceptionMapper implements ExceptionMapper<UnrecognizedPropertyException> {

    @Override
    public Response toResponse(UnrecognizedPropertyException exception) {
        // Prepare a user-friendly error response
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error", "Invalid property in request");
        errorDetails.put("message", exception.getMessage());
        errorDetails.put("invalidProperty", exception.getPropertyName());
        errorDetails.put("allowedProperties", exception.getKnownPropertyIds());

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorDetails)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}