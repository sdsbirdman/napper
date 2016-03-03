package com.hs.model.base;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Model implements Serializable
{
	/*--------------------------------------------
	|             C O N S T A N T S             |
	============================================*/

	private static final String DASH = "-";
	private static final String BLANK = "";

	/*--------------------------------------------
	|    I N S T A N C E   V A R I A B L E S    |
	============================================*/

	@Id private String id;

	@Index private transient long version = 0;

	@Index private Date lastUpdate = new Date();

	@Index private Status status = Status.ACTIVE;

	private Date creationDate = new Date();

	/*--------------------------------------------
	|         C O N S T R U C T O R S           |
	============================================*/

	public Model()
	{
	}

	public Model( String id )
	{
		this.id = id;
	}

	/*--------------------------------------------
	|   P U B L I C    A P I    M E T H O D S   |
	============================================*/

	public static String getUUID()
	{
		return UUID.randomUUID().toString().replace( DASH, BLANK );
	}

	/*--------------------------------------------
	|    N O N - P U B L I C    M E T H O D S   |
	============================================*/

	/*--------------------------------------------
	|  A C C E S S O R S / M O D I F I E R S    |
	============================================*/

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public Date getLastUpdate()
	{
		return lastUpdate;
	}

	public void setLastUpdate( Date lastUpdate )
	{
		this.lastUpdate = lastUpdate;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus( Status status )
	{
		this.status = status;
	}

	public Date getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate( Date creationDate )
	{
		this.creationDate = creationDate;
	}

	/*--------------------------------------------
	|       I N L I N E    C L A S S E S        |
	============================================*/

	public static enum Status
	{
		PENDING, ACTIVE, DISABLED, DELETED;
	}
}
