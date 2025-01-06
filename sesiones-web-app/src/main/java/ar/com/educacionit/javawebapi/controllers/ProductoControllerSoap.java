package ar.com.educacionit.javawebapi.controllers;
import java.io.IOException;

import ar.com.educacionit.client.SoapClient;
import ar.com.educacionit.client.impl.SoapClientCXFImpl;
import ar.com.educacionit.repository.dtos.ProductoDTO;
import jakarta.servlet.ServletException;
//<=8 usar javax.servlet
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//1 covierto en WebServlet
@WebServlet("/ws/producto")
public class ProductoControllerSoap extends HttpServlet{
	
	@Override
	protected void doGet(
			HttpServletRequest req, //viene todos los parametros del front
			HttpServletResponse resp
	) 
	throws ServletException, IOException {
		//BUSCA UN PRODUCTO EN LA DB POR SU ID
		 
		System.out.println("ProductoControllerSoap");
		
		//captro el parametro desde el req
		String id = req.getParameter("id");//es numero
		Long idL = Long.parseLong(id);
		
		//usamos la interface definida en el cliente
		SoapClient soapClient = new SoapClientCXFImpl();//aca se cual es la imple!!
		
		ProductoDTO productoDto = soapClient.getById(idL);
		
		//guarda en la sesion el objeto de la DB
		//se guarda con Object
		req.getSession().setAttribute("PRODUCTO", productoDto);
		
		//redirect
		getServletContext().getRequestDispatcher("/datos.jsp").forward(req, resp);
	}
	
}
