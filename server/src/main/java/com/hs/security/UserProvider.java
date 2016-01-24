package com.hs.security;

import com.google.inject.Provider;
import com.hs.model.BaseUser;

import org.jboss.resteasy.plugins.guice.RequestScoped;
import org.jboss.resteasy.spi.HttpRequest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.util.List;

/**
 * Created by Evan Ruff on 1/18/2016.
 */
@RequestScoped
public class UserProvider implements Provider<BaseUser>
{
	@Inject @Context private HttpServletRequest request;

	@Override
	public BaseUser get()
	{
		String userId = request.getHeader( "user_id" );
		String authenticationToken = request.getHeader( "auth_token" );
		return null;
	}
}
