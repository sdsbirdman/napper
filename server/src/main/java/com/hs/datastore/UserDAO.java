package com.hs.datastore;

import com.googlecode.objectify.cmd.Query;
import com.hs.model.User;
import org.mindrot.jbcrypt.BCrypt;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class UserDAO extends BaseDAO
{
	/*--------------------------------------------
	|             C O N S T A N T S             |
	============================================*/

	private static final String EMAIL_LIST = "emailList";

	/*--------------------------------------------
	|    I N S T A N C E   V A R I A B L E S    |
	============================================*/

	/*--------------------------------------------
	|         C O N S T R U C T O R S           |
	============================================*/

	/*--------------------------------------------
	|   P U B L I C    A P I    M E T H O D S   |
	============================================*/

	public User getById( String userId )
	{
		ofy().clear();
		return ofy().load().type( User.class ).id( userId ).now();
	}

	public User getByEmailAndPassword( String email, String password )
	{
		User user = getByEmail( email );
		if ( user != null )
		{
			String encPass = BCrypt.hashpw( password, user.getSalt() );
			if ( encPass.equals( user.getPassword() ) )
			{
				return user;
			}
		}
		return null;
	}

	public boolean changeUserPassword( String userId, String password )
	{
		User user = getById( userId );
		if ( user != null )
		{
			String salt = BCrypt.gensalt();
			String encPass = BCrypt.hashpw( password, salt );

			user.setPassword( encPass );
			user.setSalt( salt );
			user.setMustChangePassword( false );
			ofy().save().entity( user ).now();

			return true;
		}
		else
		{
			return false;
		}
	}

	/*-------------------------------------------------
	|              G E T    M E T H O D S             |
	==================================================*/

	public User getByEmail( String email )
	{
		ofy().clear();
		Query<User> query = ofy().load().type( User.class ).filter( EMAIL_LIST, email );
		return query.first().now();
	}

	/*--------------------------------------------
	|  A C C E S S O R S / M O D I F I E R S    |
	============================================*/

	/*--------------------------------------------
	|       I N L I N E    C L A S S E S        |
	============================================*/
}
