package com.hs.exceptionMapping;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.hs.model.ApplicationException;
import com.hs.model.CodedException;

import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Evan Ruff on 1/18/2016.
 */
@Provider
@Singleton
public class BaseExceptionMapper implements ExceptionMapper<ApplicationException> {
    /*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/

    /*--------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/

    /*--------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/

    /*--------------------------------------------
    |   P U B L I C    A P I    M E T H O D S   |
    ============================================*/

    @Override
    public Response toResponse(ApplicationException exception) {

        try {
            StringWriter response = new StringWriter();
            JsonFactory factory = new JsonFactory();
            JsonGenerator g = factory.createGenerator(response);
            g.writeStartObject();
            g.writeStringField("message", exception.getMessage());
            if (exception instanceof CodedException) {
                CodedException codeEx = (CodedException) exception;
                g.writeNumberField("error_code", codeEx.getCode());
            } else {
                g.writeNumberField("error_code", 0);
            }
            g.writeEndObject();
            g.close();

            return Response.serverError().entity(response.toString()).type(MediaType.APPLICATION_JSON).build();
        } catch (IOException ioEx) {
            return Response.serverError().entity("error").type(MediaType.APPLICATION_JSON).build();

        }
    }

    /*--------------------------------------------
    |    N O N - P U B L I C    M E T H O D S   |
    ============================================*/

    /*--------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/

    /*--------------------------------------------
    |       I N L I N E    C L A S S E S        |
    ============================================*/
}
