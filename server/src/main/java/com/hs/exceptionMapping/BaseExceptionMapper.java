package com.hs.exceptionMapping;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by Evan Ruff on 1/18/2016.
 */
public class BaseExceptionMapper implements ExceptionMapper<WebApplicationException>
{
	@Override
	public Response toResponse( WebApplicationException exception )
	{
		return null;
	}
}
