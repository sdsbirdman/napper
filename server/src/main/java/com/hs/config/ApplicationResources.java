package com.hs.config;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.hs.user.LoginService;
import com.hs.user.UserService;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Evan Ruff on 1/18/2016.
 */
public class ApplicationResources extends AbstractModule
{
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

	/*--------------------------------------------
	|    N O N - P U B L I C    M E T H O D S   |
	============================================*/

	@Override
	protected void configure()
	{
		try
		{
			Properties configProps = new Properties();
			configProps.load( getClass().getClassLoader().getResourceAsStream( "config.properties" ) );
			Names.bindProperties( binder(), configProps );
		}
		catch ( IOException ioEx )
		{
			System.err.println( "Could not load properties file" );
			ioEx.printStackTrace( System.err );
		}

		bind( ApiListingResource.class );
		bind( SwaggerSerializers.class );

		bind( UserService.class );
		bind( LoginService.class );
	}

	/*--------------------------------------------
	|  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/

	/*--------------------------------------------
	|       I N L I N E    C L A S S E S        |
	============================================*/
}
