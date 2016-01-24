package com.hs.auto.app.client;

import com.github.gilbertotorrezan.gwtviews.client.NavigationManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.hs.auto.oauth.client.OAuthClientProcessor;

public class Admin implements EntryPoint
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
	public void onModuleLoad()
	{
		// UniversalAnalyticsTracker.configure("MY-TRACKER-ID");

		if (! OAuthClientProcessor.isHandleOAuthReturn())
		{
			NavigationManager.setUserPresenceManager( new UserPresenceManager() );
			NavigationManager.start( RootPanel.get() );
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
	============================================*/}
