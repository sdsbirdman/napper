package com.hs.model;

import java.util.Date;

/**
 * Created by Evan Ruff on 1/18/2016.
 */
public class BaseUser
{
	private String name;
	private boolean isAdmin = true;
	private Date created = new Date();

	public BaseUser( String name )
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public boolean isAdmin()
	{
		return isAdmin;
	}

	public void setAdmin( boolean admin )
	{
		isAdmin = admin;
	}

	public Date getCreated()
	{
		return created;
	}

	public void setCreated( Date created )
	{
		this.created = created;
	}
}
