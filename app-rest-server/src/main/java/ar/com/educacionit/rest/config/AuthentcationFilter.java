package ar.com.educacionit.rest.config;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import ar.com.educacionit.domain.Role;
import ar.com.educacionit.domain.User;
import ar.com.educacionit.exceptions.ServiceException;
import ar.com.educacionit.services.UserService;
import ar.com.educacionit.services.impl.UserServiceImpl;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AuthentcationFilter implements ContainerRequestFilter {

	@Context // cdi
	private ResourceInfo resourceInfo;

	private static final String AUTHORIZATION_PROPERTY = "Authorization";

	private UserService userService = new UserServiceImpl();

    /**
     * A preflight request is an OPTIONS request
     * with an Origin header.
     */
    private static boolean isPreflightRequest(ContainerRequestContext request) {
        return request.getHeaderString("Origin") != null
                && request.getMethod().equalsIgnoreCase("OPTIONS");
    }
    
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

        // If it's a preflight request, we abort the request with
        // a 200 status, and the CORS headers are added in the
        // response filter method below.
        if (isPreflightRequest(requestContext)) {
        	requestContext.abortWith(Response.ok().build());
            return;
        }
		
		// GUARDS
		Method metodo = resourceInfo.getResourceMethod(); // JAVA REFLECTION API

		if (metodo.isAnnotationPresent(PermitAll.class)) {// tiene una annotation? @Algo
			return;
		}

		if (metodo.isAnnotationPresent(DenyAll.class)) {// tiene una annotation? @Algo
			requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity("Not allow to be here").build());
		}

		try {			
			if (metodo.isAnnotationPresent(RolesAllowed.class)) {
				// si no hay headers
				List<String> authorization = requestContext.getHeaders().get(AUTHORIZATION_PROPERTY);// viene o no viene
				if (authorization == null || authorization.isEmpty()) {
					requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity("No heades Authorization").build());
				}
				
				String jwt = authorization.get(0);
	
				DecodedJWT decoded = decodeJWT(jwt);
				
				String username = decoded.getClaim("USERNAME").as(String.class);//con el username busco el usuario en la db
		
				User user = userService.getUserByUsername(username);
				
				//obtengo los @RolesAllowed del metodo
                RolesAllowed rolesAnnotation = metodo.getAnnotation(RolesAllowed.class);                
                String[] roles = rolesAnnotation.value();//["ADMIN","DR"]
                
                boolean tieneElRol = false;
                
                List<Role> rolesDelUsuario = user.getRoles();
                
                for(int i=0; !tieneElRol && i < roles.length;i++) {                	
            		for(int j=0; !tieneElRol && j < rolesDelUsuario.size() ;i++) {
                		tieneElRol = rolesDelUsuario.get(j).getRole().equals(roles[i]); 
                	}
                }//crtl + shift+ m
				
                if(!tieneElRol) {
                	requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity("You can not access this resource").build());
                }
			
			}
		} catch (ServiceException e) {
        	requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity("You can not access this resource").build());
		}
	}

	private DecodedJWT decodeJWT(String jwt) {
		Algorithm algorithm = Algorithm.HMAC256("MI_CLAVE_SECRETA");

		// Crear el verificador del JWT
		JWTVerifier verifier = JWT.require(algorithm).build();
		
		DecodedJWT decoded = verifier.verify(jwt);
		return decoded;
	}

}
