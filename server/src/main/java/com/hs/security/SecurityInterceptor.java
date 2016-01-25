package com.hs.security;

import com.google.common.base.Splitter;
import com.google.common.io.BaseEncoding;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.hs.model.BaseUser;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by gte619n on 1/25/16.
 */
@javax.ws.rs.ext.Provider
@ServerInterceptor
public class SecurityInterceptor implements ContainerRequestFilter
{
	/*--------------------------------------------
	|             C O N S T A N T S             |
	============================================*/

	private static final String RESOURCE_INVOKER = "org.jboss.resteasy.core.ResourceMethodInvoker";
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final ServerResponse ACCESS_DENIED = new ServerResponse( "Access denied for this resource", 401, new Headers<Object>() );
	private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse( "Nobody can access this resource", 403, new Headers<Object>() );
	private static final ServerResponse SERVER_ERROR = new ServerResponse( "INTERNAL SERVER ERROR", 500, new Headers<Object>() );

	/*--------------------------------------------
	|    I N S T A N C E   V A R I A B L E S    |
	============================================*/

	private final Provider<RequestUserProvider> userProvider;

	/*--------------------------------------------
	|         C O N S T R U C T O R S           |
	============================================*/

	@Inject
	public SecurityInterceptor( Provider<RequestUserProvider> userProvider )
	{
		this.userProvider = userProvider;
	}

	/*--------------------------------------------
	|   P U B L I C    A P I    M E T H O D S   |
	============================================*/

	@Override
	public void filter( ContainerRequestContext requestContext ) throws IOException
	{

		ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty( RESOURCE_INVOKER );
		Method method = methodInvoker.getMethod();

		if (!method.isAnnotationPresent( PermitAll.class ))
		{
			if (method.isAnnotationPresent( DenyAll.class ))
			{
				requestContext.abortWith( ACCESS_FORBIDDEN );
				return;
			}

			final MultivaluedMap<String, String> headers = requestContext.getHeaders();
			final List<String> authorization = headers.get( AUTHORIZATION_PROPERTY );

			if (authorization == null || authorization.isEmpty())
			{
				requestContext.abortWith( ACCESS_DENIED );
				return;
			}

			final String encodedToken = authorization.get( 0 ).replaceFirst( AUTHENTICATION_SCHEME + " ", "" );
			String decodedToken = new String( BaseEncoding.base64().decode( encodedToken ) );

			//Split username and password tokens
			Iterator<String> tokenItr = Splitter.on( "," ).split( decodedToken ).iterator();
			final String username = tokenItr.next();
			final String token = tokenItr.next();
			final String device = tokenItr.next();

			System.out.println( username );
			System.out.println( token );

			BaseUser user = getUser( username, token, device );

			if (user == null)
			{
				requestContext.abortWith( ACCESS_DENIED );
				return;
			}
			if (method.isAnnotationPresent( RolesAllowed.class ))
			{
				RolesAllowed rolesAnnotation = method.getAnnotation( RolesAllowed.class );
				Set<String> rolesSet = new HashSet<String>( Arrays.asList( rolesAnnotation.value() ) );

				//Is user valid?
				if (!isUserAllowed( user, rolesSet ))
				{
					requestContext.abortWith( ACCESS_DENIED );
					return;
				}
			}
		}
		else
		{
			BaseUser user = new BaseUser( "Setting it here" );
			user.setCreated( new Date( 0 ) );
			userProvider.get().setUser( user );
		}
	}

	/*--------------------------------------------
	|    N O N - P U B L I C    M E T H O D S   |
	============================================*/

	private BaseUser getUser( String username, String token, String device )
	{
		return null;
	}

	private boolean isUserAllowed( BaseUser user, Set<String> rolesSet )
	{
		return true;
	}

	/*--------------------------------------------
	|  A C C E S S O R S / M O D I F I E R S    |
	============================================*/

	/*--------------------------------------------
	|       I N L I N E    C L A S S E S        |
	============================================*/
}
