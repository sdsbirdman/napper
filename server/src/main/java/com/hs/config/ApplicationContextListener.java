package com.hs.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * Created by Evan Ruff on 1/18/2016.
 */
public class ApplicationContextListener extends GuiceServletContextListener
{
	@Override
	protected Injector getInjector()
	{
		return Guice.createInjector( new ApplicationResources(), new BaseResourceModule(), new BaseServletModule() );
	}
}
