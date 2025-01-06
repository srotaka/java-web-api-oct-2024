package ar.com.educacionit.javawebapi.controllers;
import java.io.IOException;

import ar.com.educacionit.repository.dtos.ProductoDTO;
import ar.com.educacionit.services.ProductoService;
import ar.com.educacionit.services.impl.ProductoServiceImpl;
import jakarta.servlet.ServletException;
//<=8 usar javax.servlet
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//1 covierto en WebServlet
@WebServlet("/api/producto")
public class ProductoController extends HttpServlet{
	
	@Override
	protected void doGet(
			HttpServletRequest req, //viene todos los parametros del front
			HttpServletResponse resp
	) 
	throws ServletException, IOException {
		//BUSCA UN PRODUCTO EN LA DB POR SU ID
		  
		//captro el parametro desde el req
		String id = req.getParameter("id");//es numero
		Long idL = Long.parseLong(id);
		
		//ahora instancio el servicio
		ProductoService service = new ProductoServiceImpl();
		
		//obtengo el producto usanod el servicio
		ProductoDTO productoDto = service.getById(idL);
		
		//propio de los Servlet, para escribir en el reponse
		//resp.getWriter().print(productoDto.toString());
		
		//guarda en la sesion el objeto de la DB
		//se guarda con Object
		req.getSession().setAttribute("PRODUCTO", productoDto);
		
		//redirect
		getServletContext().getRequestDispatcher("/datos.jsp").forward(req, resp);
	}
	
}
