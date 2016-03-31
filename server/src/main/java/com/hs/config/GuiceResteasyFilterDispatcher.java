package com.hs.config;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.jboss.resteasy.plugins.guice.ModuleProcessor;
import org.jboss.resteasy.plugins.server.servlet.FilterDispatcher;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Evan Ruff on 2/4/2016.
 */
@Singleton
public class GuiceResteasyFilterDispatcher extends FilterDispatcher
{
	/*--------------------------------------------
	|             C O N S T A N T S             |
	============================================*/

	/*--------------------------------------------
	|    I N S T A N C E   V A R I A B L E S    |
	============================================*/

	private Injector injector;

	/*--------------------------------------------
	|         C O N S T R U C T O R S           |
	============================================*/

	@Inject
	public GuiceResteasyFilterDispatcher( Injector injector )
	{
		this.injector = injector;
	}

	/*--------------------------------------------
	|   P U B L I C    A P I    M E T H O D S   |
	============================================*/

	@Override
	public void init( FilterConfig cfg ) throws ServletException
	{
		super.init( cfg );

		final ServletContext context = cfg.getServletContext();
		final Registry registry = ( Registry ) context.getAttribute( Registry.class.getName() );
		final ResteasyProviderFactory providerFactory = ( ResteasyProviderFactory ) context.getAttribute( ResteasyProviderFactory.class.getName() );
		final ModuleProcessor processor = new ModuleProcessor( registry, providerFactory );

		processor.processInjector( injector );
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		System.out.println(request.getRequestURI());
		if (request.getRequestURI().contains("swagger-ui")) {
			filterChain.doFilter(request, servletResponse);
		} else {
			super.doFilter(servletRequest, servletResponse, filterChain);
		}
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
