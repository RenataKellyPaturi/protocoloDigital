package br.org.pmc.controll.rest.service;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethodInvoker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;

import br.org.pmc.model.entity.impl.User;
import br.org.pmc.model.entity.impl.UserProfile;

@Provider
@ServerInterceptor
public class SecurityInterceptor implements ContainerRequestFilter {
	
	@Context
	private HttpServletRequest servletRequest;

	private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401,
			new Headers<Object>());;
	private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403,
			new Headers<Object>());;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
		Method method = methodInvoker.getMethod();
		
		if (!method.isAnnotationPresent(PermitAll.class)) {
			
			try {
				
				if (method.isAnnotationPresent(DenyAll.class)) {
					requestContext.abortWith(ACCESS_FORBIDDEN);
					return;
				}

				User userLoogged = MyHttpSessionManager.getInstance().getSessionUserLogged(servletRequest.getSession());
				
				if (userLoogged == null) {
					requestContext.abortWith(ACCESS_FORBIDDEN);
					return;
				}
				
				if (method.isAnnotationPresent(RolesAllowed.class)) {
					
					RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
					
					Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
					
					boolean hasAccess = true;
					
					for (UserProfile userProfile : userLoogged.getUserProfiles()) {
						if ( isUserAllowed(rolesSet, userProfile.getName()) ){
							hasAccess = true;
							break;
						}else{
							hasAccess = false;
						}
					}
					
					if(!hasAccess){
						requestContext.abortWith(ACCESS_DENIED);
						return;
					}

				}
				
			} catch (Exception e) {
				requestContext.abortWith(ACCESS_DENIED);
				return;
			}

		}

	}

	private boolean isUserAllowed(final Set<String> rolesSet, String userProfile) {

		boolean isAllowed = false;

		if (rolesSet.contains(userProfile)) {
			isAllowed = true;
		}
		return isAllowed;

	}

}
