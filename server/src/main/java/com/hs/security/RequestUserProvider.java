package com.hs.security;

import com.hs.model.BaseUser;

/**
 * Created by gte619n on 1/25/16.
 */
public class RequestUserProvider
{
	/*--------------------------------------------
	|             C O N S T A N T S             |
	============================================*/

	/*--------------------------------------------
	|    I N S T A N C E   V A R I A B L E S    |
	============================================*/

	private BaseUser user;

	/*--------------------------------------------
	|         C O N S T R U C T O R S           |
	============================================*/

	public RequestUserProvider()
	{
	}

	/*--------------------------------------------
	|   P U B L I C    A P I    M E T H O D S   |
	============================================*/

	/*--------------------------------------------
	|    N O N - P U B L I C    M E T H O D S   |
	============================================*/

	/*--------------------------------------------
	|  A C C E S S O R S / M O D I F I E R S    |
	============================================*/

	public BaseUser getUser()
	{
		return user;
	}

	public void setUser( BaseUser user )
	{
		this.user = user;
	}

	/*--------------------------------------------
	|       I N L I N E    C L A S S E S        |
	============================================*/
}
