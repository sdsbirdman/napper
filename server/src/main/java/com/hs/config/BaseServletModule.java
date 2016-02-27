package com.hs.config;

import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;

/**
 * Created by Evan Ruff on 2/4/2016.
 */
public class BaseServletModule extends ServletModule
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

	@Override
	protected void configureServlets()
	{
		filter( "/*" ).through( ObjectifyFilter.class );
		filter( "/*" ).through( GuiceResteasyFilterDispatcher.class );

		// Swagger Setup
		/*
		Multibinder.newSetBinder( binder(), ServletContextListener.class )
				.addBinding().to( SwaggerServletContextListener.class );

		bind( SwaggerApiListingResource.class );
		bind( SwaggerSerializers.class );

		filter( "/api/*" ).through( ApiOriginFilter.class );
		*/
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

	/*
	@Path( "/api" )
	static final class SwaggerApiListingResource extends ApiListingResourceJSON
	{
	}
	*/
}
