package com.hs.model;

/**
 * Created by Evan Ruff on 1/18/2016.
 */
public class SimpleException
{
	private String status = "error";

	private String message;

	public SimpleException()
	{
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus( String status )
	{
		this.status = status;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage( String message )
	{
		this.message = message;
	}
}
