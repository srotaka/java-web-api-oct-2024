package ar.com.educacionit.rest;

import java.security.interfaces.RSAKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import ar.com.educacionit.domain.User;
import ar.com.educacionit.exceptions.ServiceException;
import ar.com.educacionit.services.UserService;
import ar.com.educacionit.services.impl.UserServiceImpl;
import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Result;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Path("auth")
public class AuthResource {

	private UserService userService = new UserServiceImpl();

	// http://localhost:8080/app-rest/api/auth?username=bla&password=bla
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public Response login(
			@QueryParam("username") String username, 
			@QueryParam("password") String password
		) {

		ResponseBuilder response = Response.ok();
		
		try {
			User user = this.userService.getUserByUsername(username);
			Result res = BCrypt.verifyer().verify(password.getBytes(), user.getPassword().getBytes());
			if (user != null && res.verified) {
				
				String roles = this.userService.findRoles()
						.stream()
						.map(r -> r.getRole())
						.reduce((x,y) -> x.concat(",").concat(y))
						.get();
				
				try {				    
					HashMap<String, Object> headersMap = new HashMap<>();
					
			        Algorithm algorithm = Algorithm.HMAC256("MI_CLAVE_SECRETA");

			        // Fecha de expiración del token (1 hora desde ahora)
			        Date now = new Date();
			        Date expiresAt = new Date(now.getTime() + 3600 * 1000);
			        
					String jwt = JWT.create()
					        .withHeader(headersMap)
					        .withClaim("USERNAME", user.getUsername())
					        .withClaim("ROLES", roles)
					        .withExpiresAt(expiresAt)
					        .sign(algorithm);
					
					response.header("Access-Token", new String(jwt));
					
				} catch (JWTCreationException exception){
				    // Invalid Signing configuration / Couldn't convert Claims.
					response.status(Status.UNAUTHORIZED);
				}				
			}else {
				response.status(Status.UNAUTHORIZED);
			}
		} catch (ServiceException e) {
			//no auth
			response.status(Status.UNAUTHORIZED);
		}
		return response.build();
	}
}
