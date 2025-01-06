package ar.com.educacionit.beans.enums;

public enum PagesEnums {

	LOGIN_SUCCESS("login-success"),
	LOGIN("login"),
	LOGIN_ERROR("login-error"), 
	EDITAR_PRODUCTOS("editar-producto"), 
	LISTADO_PRODUCTOS("listado-productos"), 
	NUEVO_PRODUCTO("nuevo-producto"), 
	LISTADO_SITES("listado-productos-restclient");
	
	private String page;
	
	private PagesEnums(String page) {
		this.page = page;
	}

	public String getPage() {
		return page;
	}
	
}

