package com.hs.model;

import com.google.common.base.Strings;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.hs.model.base.Model;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity( name = "user" )
public class User extends Model implements Comparable<User>
{
	/*--------------------------------------------
	|             C O N S T A N T S             |
	============================================*/

	/*--------------------------------------------
	|    I N S T A N C E   V A R I A B L E S    |
	============================================*/

	private Date lastLogin = new Date();

	private String name;

	private UserType type = UserType.USER;

	private transient String password;
	private transient String salt;
	private boolean mustChangePassword = false;

	private boolean hasGoogleAuth;
	private boolean hasFacebookAuth;

	private String primaryEmail;

	@Index private Set<String> emailSet = new HashSet<>();

	private transient Set<AccessToken> tokenSet = new HashSet<>();

	private Set<String> roleSet = new HashSet<>();

	/*--------------------------------------------
	|         C O N S T R U C T O R S           |
	============================================*/

	/*--------------------------------------------
	|   P U B L I C    A P I    M E T H O D S   |
	============================================*/

	@Override
	public String toString()
	{
		StringBuilder tmp = new StringBuilder();
		tmp.append( "name:" ).append( name ).append( ", email:" ).append( primaryEmail );
		return tmp.toString();
	}

	@Override
	public int compareTo( User o )
	{
		if ( Strings.isNullOrEmpty( this.name ) ) return -1;
		return this.name.compareTo( o.name );
	}

	/*--------------------------------------------
	|    N O N - P U B L I C    M E T H O D S   |
	============================================*/

	/*--------------------------------------------
	|  A C C E S S O R S / M O D I F I E R S    |
	============================================*/

	public String getName()
	{
		return name;
	}

	public Set<AccessToken> getTokenSet()
	{
		return tokenSet;
	}

	public void setTokenSet( Set<AccessToken> tokenSet )
	{
		this.tokenSet = tokenSet;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public Date getLastLogin()
	{
		return lastLogin;
	}

	public void setLastLogin( Date lastLogin )
	{
		this.lastLogin = lastLogin;
	}

	public boolean isMustChangePassword()
	{
		return mustChangePassword;
	}

	public void setMustChangePassword( boolean mustChangePassword )
	{
		this.mustChangePassword = mustChangePassword;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword( String password )
	{
		this.password = password;
	}

	public UserType getType()
	{
		return type;
	}

	public void setType( UserType type )
	{
		this.type = type;
	}

	public String getPrimaryEmail()
	{
		return primaryEmail;
	}

	public void setPrimaryEmail( String primaryEmail )
	{
		this.primaryEmail = primaryEmail;
	}

	public String getSalt()
	{
		return salt;
	}

	public void setSalt( String salt )
	{
		this.salt = salt;
	}

	public boolean isHasGoogleAuth()
	{
		return hasGoogleAuth;
	}

	public void setHasGoogleAuth( boolean hasGoogleAuth )
	{
		this.hasGoogleAuth = hasGoogleAuth;
	}

	public boolean isHasFacebookAuth()
	{
		return hasFacebookAuth;
	}

	public void setHasFacebookAuth( boolean hasFacebookAuth )
	{
		this.hasFacebookAuth = hasFacebookAuth;
	}

	public Set<String> getEmailSet()
	{
		return emailSet;
	}

	public Set<String> getRoleSet()
	{
		return roleSet;
	}

	public void setRoleSet( Set<String> roleSet )
	{
		this.roleSet = roleSet;
	}

	/*--------------------------------------------
	|       I N L I N E    C L A S S E S        |
	============================================*/

	public static class NameComparator implements Comparator<User>
	{
		@Override
		public int compare( User o1, User o2 )
		{
			return o1.compareTo( o2 );
		}
	}

	public enum UserType
	{
		USER, AUTOMATION;
	}
}
