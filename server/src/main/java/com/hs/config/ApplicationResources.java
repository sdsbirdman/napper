package com.hs.config;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.hs.security.UserProvider;
import com.hs.user.UserService;
import org.jboss.resteasy.plugins.guice.ext.RequestScopeModule;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Evan Ruff on 1/18/2016.
 */
public class ApplicationResources extends RequestScopeModule
{
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
	}
}
