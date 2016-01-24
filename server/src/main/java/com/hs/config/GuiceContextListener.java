package com.hs.config;

import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan Ruff on 1/18/2016.
 */
public class GuiceContextListener extends GuiceResteasyBootstrapServletContextListener
{
	@Override
	protected List getModules( final ServletContext context )
	{
		final List result = new ArrayList();

		result.add( new ApplicationResources() );
		return result;
	}
}
