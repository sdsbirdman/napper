package com.hs.security;

import com.google.appengine.repackaged.com.google.api.client.util.Strings;
import com.google.common.base.Splitter;
import com.google.common.io.BaseEncoding;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.hs.AppMemcache;
import com.hs.datastore.UserDAO;
import com.hs.model.AccessToken;
import com.hs.model.User;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private final UserDAO userDAO;

	/*--------------------------------------------
	|         C O N S T R U C T O R S           |
	============================================*/

	@Inject
	public SecurityInterceptor( Provider<RequestUserProvider> userProvider, UserDAO userDAO )
	{
		this.userProvider = userProvider;
		this.userDAO = userDAO;
	}

	/*--------------------------------------------
	|   P U B L I C    A P I    M E T H O D S   |
	============================================*/

	@Override
	public void filter( ContainerRequestContext requestContext ) throws IOException
	{

		ResourceMethodInvoker methodInvoker = ( ResourceMethodInvoker ) requestContext.getProperty( RESOURCE_INVOKER );
		Method method = methodInvoker.getMethod();
		if (isSwaggerMethod(method)) {
			return;
		}
		if ( !method.isAnnotationPresent( PermitAll.class ) )
		{
			if ( method.isAnnotationPresent( DenyAll.class ) )
			{
				requestContext.abortWith( ACCESS_FORBIDDEN );
				return;
			}

			final MultivaluedMap<String, String> headers = requestContext.getHeaders();
			final List<String> authorization = headers.get( AUTHORIZATION_PROPERTY );

			if ( authorization == null || authorization.isEmpty() )
			{
				requestContext.abortWith( ACCESS_DENIED );
				return;
			}

			final String encodedToken = authorization.get( 0 ).replaceFirst( AUTHENTICATION_SCHEME + " ", "" );
			String decodedToken = new String( BaseEncoding.base64().decode( encodedToken ) );
			User user = getUser( decodedToken );

			if ( user == null )
			{
				requestContext.abortWith( ACCESS_DENIED );
				return;
			}
			if ( method.isAnnotationPresent( RolesAllowed.class ) )
			{
				RolesAllowed rolesAnnotation = method.getAnnotation( RolesAllowed.class );
				Set<String> rolesSet = new HashSet<String>( Arrays.asList( rolesAnnotation.value() ) );

				//Is user valid?
				if ( !isUserAllowed( user, rolesSet ) )
				{
					requestContext.abortWith( ACCESS_DENIED );
					return;
				}
			}
			userProvider.get().setUser( user );
		}
	}

	private boolean isSwaggerMethod(Method method) {
		return ApiListingResource.class.isAssignableFrom(method.getDeclaringClass())
				|| SwaggerSerializers.class.isAssignableFrom(method.getDeclaringClass());
	}

	/*--------------------------------------------
	|    N O N - P U B L I C    M E T H O D S   |
	============================================*/

	private User getUser( String decodedToken )
	{
		User user = AppMemcache.getUserFromAuth( decodedToken );
		if ( user != null ) return user;

		List<String> tokenItr = Splitter.on( "," ).splitToList( decodedToken );
		if ( tokenItr.size() != 3 ) return null;

		final String userId = tokenItr.get( 0 );
		final String tokenId = tokenItr.get( 1 );
		final String device = tokenItr.get( 2 );

		if ( Strings.isNullOrEmpty( userId ) || Strings.isNullOrEmpty( tokenId ) || Strings.isNullOrEmpty( device ) ) return null;
		user = userDAO.getById( userId );
		if ( user != null )
		{
			Set<AccessToken> tokenSet = user.getTokenSet();
			for ( AccessToken token : tokenSet )
			{
				String targetId = token.getToken();
				String targetDevice = token.getDeviceId();
				long expMils = token.getExpDate().getTime();
				if ( targetId.equals( tokenId ) && targetDevice.equals( device ) && ( expMils > System.currentTimeMillis() ) )
				{
					return user;
				}
			}
		}

		return null;
	}

	private boolean isUserAllowed( User user, Set<String> rolesSet )
	{
		for ( String role : rolesSet )
		{
			if ( user.getRoleSet().contains( role ) ) return true;
		}
		return false;
	}

	/*--------------------------------------------
	|  A C C E S S O R S / M O D I F I E R S    |
	============================================*/

	/*--------------------------------------------
	|       I N L I N E    C L A S S E S        |
	============================================*/
}
