package ar.com.educacionit.rest;

import java.util.List;
import java.util.Map;

import ar.com.educacionit.repository.dtos.ProductoDTO;
import ar.com.educacionit.rest.validator.ValidadorManger;
import ar.com.educacionit.services.ProductoService;
import ar.com.educacionit.services.impl.ProductoServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("producto")
@Singleton
public class ProductoResource {

	//localhost:8080/app-rest-server/api/producto/1
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id
		) {
				
		//1- service
		ProductoService productoService = new ProductoServiceImpl(); //f5 entro al contructor // ServiceLocator.getService(PepeService.class);//??tph: crear una clase que creer una instancia 
		
		try {
			//find/get: find select * / select * where id=1
			ProductoDTO dto =  productoService.getById(id);
			
			if(dto.getId() !=null) {
				return Response.ok(dto).build();//200 con datos
			}
			return Response.ok().build();//200 sin datos
		}catch(Exception e) {//500
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.build();
		}
	}
	
	@RolesAllowed("ADMIN")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
				
		ProductoService productoService = new ProductoServiceImpl(); //f5 entro al contructor // ServiceLocator.getService(PepeService.class);//??tph: crear una clase que creer una instancia 
		
		try {
			List<ProductoDTO> dto =  productoService.findAll();
					
			return Response.ok(dto).build();
		}catch(Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(ProductoDTO request) {
		
		Map<String,String> errores = ValidadorManger.getValidador("Crear_Producto").validate(request);
		
		if(!errores.isEmpty()) {
			return Response.status(Status.BAD_REQUEST)
				.entity(errores)
				.build();
		}
		
		//si todo ok
		ProductoService productoService = new ProductoServiceImpl();  
		
		//idempotencia para el post
		ProductoDTO dto = productoService.getByCodigo(request.getCodigo());
		if(dto != null) {
			return Response.ok(dto).build();
		}
		
		try{			
			request = productoService.create(request);		
			return Response.ok(request).build();
		}catch(Exception e) {
			//desafio para mandar una respuesta
			return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(e.getMessage())
				.build();
		}
	}
	
	@Path("/{id}")
	@DELETE
	public Response eliminar(@PathParam("id") Long id) {
		ProductoService productoService = new ProductoServiceImpl();  
		productoService.eliminar(id);
		return Response.ok().build();
	}
	
	@Path("/{id}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response actualizar(
			@PathParam("id") Long id,
			ProductoDTO productoActualizar) {
		
		//agregar validador como en el post
		ProductoService productoService = new ProductoServiceImpl();  
		ProductoDTO pDb = productoService.getById(id);
		
		//un put sin id ni codigo
		if(pDb == null) {
			return Response.status(Status.NO_CONTENT).build();
		}
		
		try {
			productoActualizar.setId(id);
			productoService.actualizar(productoActualizar);		
			return Response.ok()
					.entity(productoActualizar)
					.build();
		}catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(productoActualizar)
					.build();
		}
	}
}
