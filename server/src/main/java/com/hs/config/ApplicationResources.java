package com.hs.config;

import com.google.inject.name.Names;
import com.hs.exceptionMapping.BaseExceptionMapper;
import com.hs.security.RequestUserProvider;
import com.hs.security.SecurityInterceptor;
import com.hs.user.UserService;
import org.jboss.resteasy.plugins.guice.RequestScoped;
import org.jboss.resteasy.plugins.guice.ext.RequestScopeModule;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Evan Ruff on 1/18/2016.
 */
public class ApplicationResources extends RequestScopeModule
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
		super.configure();
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

		bind( UserService.class );
		bind( BaseExceptionMapper.class );
		bind( SecurityInterceptor.class );
		bind( RequestUserProvider.class ).in( RequestScoped.class );

		// bind( Principal.class ).to( StatelessUserPrincipal.class );
	}

	/*--------------------------------------------
	|  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/

	/*--------------------------------------------
	|       I N L I N E    C L A S S E S        |
	============================================*/
}
