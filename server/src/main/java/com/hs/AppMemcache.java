package com.hs;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheService.IdentifiableValue;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.hs.model.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class AppMemcache
{
	/*--------------------------------------------
	|             C O N S T A N T S             |
	============================================*/

	private static final String MEM_USER = "User:";

	public static final String DEFAULT_TIMEZONE = "US/Eastern";

	public static final Expiration twelveHours = Expiration.byDeltaSeconds( 12 * 60 * 60 );

	/*--------------------------------------------
	|    I N S T A N C E   V A R I A B L E S    |
	============================================*/

	private static final MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	static
	{
		syncCache.setErrorHandler( ErrorHandlers.getConsistentLogAndContinue( Level.FINE ) );
	}

	/*----------------------------------------------
	|           U S E R   M E T H O D S            |
	==============================================*/

	public static User getUserFromAuth( String authorization )
	{
		Object user = syncCache.get( MEM_USER + authorization );
		if (user != null) return (User) user;
		return null;
	}

	public static void setUserForAuth( String authorization, User user )
	{
		syncCache.put( MEM_USER + authorization, user, twelveHours );
	}

	/*----------------------------------------------
	|          B A S I C   M E T H O D S           |
	==============================================*/

	public static boolean clear( String key )
	{
		return syncCache.delete( key );
	}

	public static void increment( String key, long value, long initial )
	{
		if (value > 0) syncCache.increment( key, value, initial );
	}

	public static void increment( String key, long value )
	{
		if (value > 0) syncCache.increment( key, value );
	}

	public static long getLong( String key )
	{
		Object longValue = syncCache.get( key );
		if (longValue == null)
		{
			return 0;
		}
		else
		{
			return ( (Long) longValue ).intValue();
		}
	}

	public static String getString( String key )
	{
		Object value = syncCache.get( key );
		if (value == null)
		{
			return null;
		}
		else
		{
			return ( (String) value );
		}
	}

	public static Date getDate( String key )
	{
		Object value = syncCache.get( key );
		if (value == null)
		{
			return null;
		}
		else
		{
			return ( (Date) value );
		}
	}

	/*----------------------------------------------
	|     C O L L E C T I O N   M E T H O D S      |
	==============================================*/

	public static <T> boolean putStrictSet( String key, T objectToPut )
	{
		return putStrictSet( syncCache, key, objectToPut );
	}

	@SuppressWarnings( "unchecked" )
	public static <T> boolean putStrictSet( MemcacheService service, String key, T objectToPut )
	{
		IdentifiableValue oldValue = service.getIdentifiable( key );
		if (oldValue == null)
		{
			Set<T> cacheSet = new HashSet<T>();
			cacheSet.add( objectToPut );
			service.put( key, cacheSet );
			return true;
		}
		else
		{
			Set<T> cacheSet = (Set<T>) oldValue.getValue();
			cacheSet.add( objectToPut );
			if (service.putIfUntouched( key, oldValue, cacheSet ))
			{
				return true;
			}
			else
			{
				return putStrictSet( service, key, objectToPut );
			}
		}
	}

	public static <T extends Set<?>> T getSet( String key )
	{
		return getSet( syncCache, key );
	}

	@SuppressWarnings( "unchecked" )
	public static <T extends Set<?>> T getSet( MemcacheService service, String key )
	{
		Object o = service.get( key );
		if (o == null) return null;
		return (T) o;
	}
}
