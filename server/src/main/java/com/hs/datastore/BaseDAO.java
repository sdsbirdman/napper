package com.hs.datastore;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.impl.translate.opt.joda.JodaTimeTranslators;
import com.hs.model.User;

public class BaseDAO
{
	/*--------------------------------------------
	|             C O N S T A N T S             |
	============================================*/

	private static final long START_LAG = 1 * 60 * 1000;

	protected static final String ID = "id";

	protected static final String GREATER = " >";
	protected static final String GREATER_EQUAL = " >=";
	protected static final String LESS = " <";
	protected static final String LESS_EQUAL = " <=";
	protected static final String NOT_EQUAL = " !=";

	protected static final String DATE = "date";
	protected static final String START_DATE = "startDate";
	protected static final String END_DATE = "endDate";
	protected static final String ENGINE_DATE = "engineDate";
	protected static final String TIME_PERIOD = "timePeriod";
	protected static final String CREATION_DATE = "creationDate";

	protected static final String ACTIVE = "active";
	protected static final String USER_STATUS = "status";

	protected static final String USER_ID = "userId";

	protected static final String DESC = "-";
	protected static final String IN = " IN";

	protected static final String LAST_UPDATE = "lastUpdate";

	/*--------------------------------------------
	|    I N S T A N C E   V A R I A B L E S    |
	============================================*/

	private static boolean isInit = false;
	static
	{
		if (! isInit)
		{
			isInit = true;
			reinit();
		}
	}

	/*--------------------------------------------
	|         C O N S T R U C T O R S           |
	============================================*/

	/*--------------------------------------------
	|   P U B L I C    A P I    M E T H O D S   |
	============================================*/

	public static final void reinit()
	{
		JodaTimeTranslators.add( ObjectifyService.factory() );

		ObjectifyService.register( User.class );
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
