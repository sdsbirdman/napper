package com.hs.user;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.hs.model.ApplicationException;
import com.hs.model.CodedException;
import com.hs.model.User;
import com.hs.security.RequestUserProvider;

import javax.annotation.security.PermitAll;
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
	/*--------------------------------------------
	|             C O N S T A N T S             |
    ============================================*/

    /*--------------------------------------------
	|    I N S T A N C E   V A R I A B L E S    |
    ============================================*/

	private final RequestUserProvider userProvider;

    /*--------------------------------------------
	|         C O N S T R U C T O R S           |
    ============================================*/

	@Inject
	UserService( RequestUserProvider userProvider )
	{
		this.userProvider = userProvider;
	}

    /*--------------------------------------------
	|   P U B L I C    A P I    M E T H O D S   |
    ============================================*/

	@GET
	@Path( "/{username}" )
	@PermitAll
	public User hello( @PathParam( "username" ) String username )
	{
		if (Strings.isNullOrEmpty( username ))
		{
			throw new UserNotFoundException( "Must include username" );
		}

		User authorizedUser = this.userProvider.getUser();
		return authorizedUser;
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

	public static class UserNotFoundException extends ApplicationException implements CodedException
	{
		public UserNotFoundException( String message )
		{
			super( message );
		}

		@Override
		public int getCode()
		{
			return 1000;
		}
	}
}
