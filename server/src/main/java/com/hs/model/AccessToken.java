package com.hs.model;

import com.google.common.base.Predicate;
import com.hs.model.base.Model;

import java.io.Serializable;
import java.util.Date;

public class AccessToken implements Serializable
{
	/*--------------------------------------------
	|             C O N S T A N T S             |
    ============================================*/

	private static final String DEFAULT_DEVICE_ID = "web";
	private static final long EXPIRATION_DAYS_MILS = 30 * 24 * 60 * 60 * 1000;

    /*--------------------------------------------
	|    I N S T A N C E   V A R I A B L E S    |
    ============================================*/

	private String token;
	private Date expDate;
	private String deviceId = DEFAULT_DEVICE_ID;

    /*--------------------------------------------
	|         C O N S T R U C T O R S           |
    ============================================*/

	public AccessToken()
	{
		this.token = Model.getUUID();
		this.expDate = new Date( System.currentTimeMillis() + EXPIRATION_DAYS_MILS );
		this.deviceId = DEFAULT_DEVICE_ID;
	}

	public AccessToken( String token )
	{
		this.token = token;
		this.expDate = new Date( System.currentTimeMillis() + EXPIRATION_DAYS_MILS );
		this.deviceId = DEFAULT_DEVICE_ID;
	}

	public AccessToken( String token, Date expDate, String deviceId )
	{
		super();
		this.token = token;
		this.expDate = expDate;
		this.deviceId = deviceId;
	}

	public AccessToken( String token, Date expDate )
	{
		super();
		this.token = token;
		this.expDate = expDate;
	}

    /*--------------------------------------------
	|   P U B L I C    A P I    M E T H O D S   |
    ============================================*/

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( deviceId == null ) ? 0 : deviceId.hashCode() );
		result = prime * result + ( ( token == null ) ? 0 : token.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( this == obj ) { return true; }
		if ( obj == null ) { return false; }
		if ( getClass() != obj.getClass() ) { return false; }
		AccessToken other = ( AccessToken ) obj;

		if ( ( deviceId == null ) && ( other.deviceId != null ) ) { return false; }
		else if ( !deviceId.equals( other.deviceId ) ) { return false; }
		if ( ( token == null ) && ( other.token != null ) ) { return false; }
		else if ( !token.equals( other.token ) ) { return false; }
		return true;
	}

    /*--------------------------------------------
	|    N O N - P U B L I C    M E T H O D S   |
    ============================================*/

    /*--------------------------------------------
	|  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/

	public String getToken()
	{
		return token;
	}

	public void setToken( String token )
	{
		this.token = token;
	}

	public Date getExpDate()
	{
		return expDate;
	}

	public void setExpDate( Date expDate )
	{
		this.expDate = expDate;
	}

	public String getDeviceId()
	{
		return deviceId;
	}

	public void setDeviceId( String deviceId )
	{
		this.deviceId = deviceId;
	}

    /*--------------------------------------------
	|       I N L I N E    C L A S S E S        |
    ============================================*/

	public static class ValidFilter implements Predicate<AccessToken>
	{
		private final AccessToken targetToken;

		public ValidFilter( AccessToken token )
		{
			this.targetToken = token;
		}

		public ValidFilter( String token )
		{
			this.targetToken = new AccessToken( token );
		}

		@Override
		public boolean apply( AccessToken token )
		{
			if ( token == null ) { return false; }
			if ( !token.equals( this.targetToken ) ) { return false;}
			return token.getExpDate().getTime() > System.currentTimeMillis();
		}
	}

	public static class UnexpiredFilter implements Predicate<AccessToken>
	{
		@Override
		public boolean apply( AccessToken token )
		{
			return token.getExpDate().getTime() > System.currentTimeMillis();
		}
	}
}
