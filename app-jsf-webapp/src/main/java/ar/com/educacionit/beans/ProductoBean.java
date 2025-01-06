package ar.com.educacionit.beans;

import java.util.List;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import ar.com.educacionit.beans.enums.PagesEnums;
import ar.com.educacionit.domain.TipoProducto;
import ar.com.educacionit.exceptions.ServiceException;
import ar.com.educacionit.repository.dtos.ProductoDTO;
import ar.com.educacionit.services.ProductoService;
import ar.com.educacionit.services.impl.ProductoServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ProductoBean {

	private ProductoService ps = new ProductoServiceImpl();
	
	private ProductoDTO producto = new ProductoDTO();
	
	private String mensajeError;
	
	private TipoProducto tipoProducto = new TipoProducto();			
	
	private List<ProductoDTO> productos; 
	
	@PostConstruct
	private void loadProductos() {
		this.productos = this.findProductos();
	}
	
	public List<ProductoDTO> findProductos() {
		
		List<ProductoDTO> productos = ps.findAll();
		
		return productos;
	}
	
	/**
	 * Cargar producto para editar
	 * @param codigo
	 */
	public String editarProducto(String codigo) {
		PagesEnums target = PagesEnums.EDITAR_PRODUCTOS;
		this.producto = this.ps.getByCodigo(codigo);
		return target.getPage();
	}
	
	public String updateProducto() {
		
		PagesEnums target = PagesEnums.LISTADO_PRODUCTOS;
		
		try {
			this.producto.setTipoProducto(this.tipoProducto);
			this.ps.actualizar(producto);
		}catch (Exception e) {
			e.printStackTrace();
			this.mensajeError = e.getCause().getMessage();
			target = PagesEnums.EDITAR_PRODUCTOS;
		}
		return target.getPage();		
	}
	
	public String eliminarProducto(Long id) {
		
		PagesEnums target = PagesEnums.LISTADO_PRODUCTOS;
		
		if(id != null) {
		
			try {
				this.ps.eliminar(id);
			} catch (RuntimeException e) {
				this.mensajeError = e.getMessage();				
			}
		}
		
		return target.getPage();
	}
	
	public TipoProducto[] getTipoProductos() {
		TipoProducto[] aux;
		try {
			List<TipoProducto> tipos = this.ps.findTipoProductos();
			aux = tipos.toArray(new TipoProducto[] {});
		} catch (ServiceException e) {
			e.printStackTrace();
			aux = new TipoProducto[0];
		}
		return aux;
	}
	
	public String crearNuevoProducto() {
		
		PagesEnums target = PagesEnums.LISTADO_PRODUCTOS;
		
		try {
			this.producto.setTipoProducto(this.tipoProducto);		
			ProductoDTO nuevo =  this.ps.create(this.producto);
			this.productos.add(nuevo);
		}catch (Exception e) {
			this.mensajeError = e.getMessage() + " - " +e.getCause().getMessage();
			target = PagesEnums.NUEVO_PRODUCTO;
		}
		
		return target.getPage();
	}

	public void onRowEdit(RowEditEvent<ProductoDTO> event) {
		FacesMessage msg;
		ProductoDTO productoSeleccionado = event.getObject();
		try {
			if(!productoSeleccionado.getTipoProducto().getId().equals(this.tipoProducto.getId())) {
				productoSeleccionado.getTipoProducto().setId(this.tipoProducto.getId());
			}
			this.ps.actualizar(productoSeleccionado);
			msg = new FacesMessage("Producto editado ", productoSeleccionado.getId().toString());
			this.productos = this.findProductos();
		} catch (RuntimeException e) {
			msg = new FacesMessage(e.getMessage(), productoSeleccionado.getId().toString());
		}
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void onRowCandel(RowEditEvent<ProductoDTO> event) {
		ProductoDTO productoSeleccionado = event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", productoSeleccionado.getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void eliminarProducto() {
		FacesMessage msg;
		try {
			this.ps.eliminar(producto.getId());
			msg = new FacesMessage("Producto eliminado ", producto.getId().toString());
			this.productos.remove(producto);
			this.producto = null;
		} catch (Exception e) {
			msg = new FacesMessage("Error eliminando producto", e.getMessage());
		}
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void onRowSelect(SelectEvent<ProductoDTO> event) {
		this.producto = event.getObject();
	}
	
	public ProductoDTO getProducto() {
		return producto;
	}

	public void setProducto(ProductoDTO producto) {
		this.producto = producto;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public List<ProductoDTO> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoDTO> productos) {
		this.productos = productos;
	}
	 
}
