package com.hs.config;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.RequestScoped;
import com.googlecode.objectify.ObjectifyFilter;
import com.hs.security.RequestUserProvider;
import com.hs.security.SecurityInterceptor;

import javax.inject.Singleton;

/**
 * Created by gte619n on 2/3/16.
 */
public class BaseResourceModule extends AbstractModule
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
	protected void configure()
	{
		bind( BaseExceptionMapper.class );
		bind( SecurityInterceptor.class );

		bind( RequestUserProvider.class ).in( RequestScoped.class );
		bind( ObjectifyFilter.class ).in( Singleton.class );

		// bind( ApiListingResource.class );
		// bind( SwaggerSerializers.class );
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
