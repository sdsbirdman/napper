package com.hs.model.base;

import com.googlecode.objectify.annotation.Index;

import java.util.Date;

public class DateModel extends Model
{
	/*--------------------------------------------
	|             C O N S T A N T S             |
	============================================*/

	/*--------------------------------------------
	|    I N S T A N C E   V A R I A B L E S    |
	============================================*/

	@Index private Date startDate = new Date();

	@Index private Date endDate;

	@Index private boolean active;

	/*--------------------------------------------
	|         C O N S T R U C T O R S           |
	============================================*/

	public DateModel()
	{
	}

	public DateModel( String guid )
	{
		super( guid );
	}

	/*--------------------------------------------
	|   P U B L I C    A P I    M E T H O D S   |
	============================================*/

	public boolean isCurrent()
	{
		return ( ( getEndDate() == null ) || ( getEndDate().after( new Date() ) ) );
	}

	public void setActivityInformation()
	{
		this.active = isCurrent();
	}

	/*--------------------------------------------
	|    N O N - P U B L I C    M E T H O D S   |
	============================================*/

	/*--------------------------------------------
	|  A C C E S S O R S / M O D I F I E R S    |
	============================================*/

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate( Date startDate )
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate( Date endDate )
	{
		this.endDate = endDate;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive( boolean active )
	{
		this.active = active;
	}

	/*--------------------------------------------
	|       I N L I N E    C L A S S E S        |
	============================================*/
}
