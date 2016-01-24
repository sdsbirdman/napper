package com.hs.user;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.hs.model.BaseUser;
import com.hs.security.UserProvider;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Created by Evan Ruff on 1/18/2016.
 */
@Path( "user" )
@Produces( "application/json" )
public class UserService
{
	@Inject UserProvider userProvider;

	@GET
	public BaseUser hello( @Named( "testUsername" ) String username )
	{
		BaseUser user = userProvider.get();
		return new BaseUser( "Evan" );
	}
}
