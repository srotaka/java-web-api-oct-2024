package ar.com.educacionit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.com.educacionit.domain.Producto;
import ar.com.educacionit.domain.TipoProducto;
import ar.com.educacionit.repository.ProductoRepository;
import ar.com.educacionit.repository.TipoProductoRepository;

@Controller
public class ProductoController {

	@Autowired // -> inyectar instancias
	private ProductoRepository pr;
	
	@Autowired // -> inyectar instancias
	private TipoProductoRepository tpr;

	@RequestMapping("/list")
	public String listado(
			Model model
		) {

		List<Producto> productos = this.pr.findAll();// select * from producto

		System.out.println(productos);
		
		model.addAttribute("productos",productos);// esto despues se visualiza en listado.html
		
		return "listado";//crear enum con sus vistas LISTADO > "listado"
	}
	// ctrl+shift+f
	
	@RequestMapping("/delete/{id}")
	public String delete(
			@PathVariable(name="id") Long id 
		) {
		
		this.pr.deleteById(id);
		
		//redireccion al listado
		return "redirect:/list";
	}
	
	@RequestMapping("/new")
	public ModelAndView nuevo() {
		Producto empty = new Producto();
		
		ModelAndView modelAndView =  new ModelAndView("nuevoProducto");
		
		modelAndView.addObject("producto",empty);

		List<TipoProducto> tiposDisponibles = tpr.findAll();
		
		modelAndView.addObject("tiposProductos", tiposDisponibles);
		
		return modelAndView;
	}

	//save por POST
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String save(
			@ModelAttribute(name="producto") Producto producto
		) {
		
		//if(aplico mis validaciones)
		this.pr.save(producto);
		
		return "redirect:/list"; //quiero volver al listado"
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(
			@PathVariable(name="id") Long id,
			Model model
		) {
		
		Producto productoAEditar =  this.pr.findById(id).get();
		
		model.addAttribute("producto", productoAEditar);
		
		List<TipoProducto> tiposDisponibles = tpr.findAll();
		
		model.addAttribute("tiposProductos", tiposDisponibles);
		
		//redireccion al listado
		return "edit";
	}
	
	//upate port post
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String update(
		@ModelAttribute(name="producto") Producto producto
	) {
		this.pr.save(producto);
		
		return "redirect:/list"; //quiero volver al listado"
	}
}
