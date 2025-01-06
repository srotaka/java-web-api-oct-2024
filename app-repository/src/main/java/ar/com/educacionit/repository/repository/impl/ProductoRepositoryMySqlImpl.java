package ar.com.educacionit.repository.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import ar.com.educacionit.domain.Producto;
import ar.com.educacionit.domain.TipoProducto;
import ar.com.educacionit.exceptions.GenericException;
import ar.com.educacionit.hibernate.HibernateUtils;
import ar.com.educacionit.repository.dtos.ProductoDTO;
import ar.com.educacionit.repository.exceptions.DBConnectionException;
import ar.com.educacionit.repository.repository.ProductoRepository;

public class ProductoRepositoryMySqlImpl implements ProductoRepository {

	//implementar todos los metodos de la interface
	private SessionFactory factory;
	
	public ProductoRepositoryMySqlImpl() {
		this.factory = HibernateUtils.getSessionFactory();//recien aca se crea la conexion(y todo lo necesario) a la base
	}
	
	@Override
	public ProductoDTO save(ProductoDTO dto) {//sin id
		//insert into tabla (c1,c2....) values(v1,v2...vn)
		Session session = this.factory.getCurrentSession();
		session.beginTransaction();
		
		//DTO > Dominio
		Producto nuevoProducto = new Producto(dto.getTitulo(), dto.getCodigo(), dto.getPrecio());//ctrlshift+i
		
		TipoProducto tipo = new TipoProducto();
		tipo.setId(dto.getTipoProducto().getId());
		
		nuevoProducto.setTipoProducto(tipo);
		
		session.persist(nuevoProducto);
		session.getTransaction().commit();
		session.close();
		
		dto.setId(nuevoProducto.getId());
		return dto;
	}

	@Override
	public ProductoDTO getById(Long id) {
		//usar hibernate para consultar a la db y que nos retorne un objeto Producto
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.beginTransaction();
			Producto producto = session.get(Producto.class, id);
			
			ProductoDTO dto = new ProductoDTO(producto.getId(),producto.getTitulo(),producto.getPrecio());
			dto.setCodigo(producto.getCodigo());
			return dto;
		}catch(HibernateException e) {
			// session.getTransaction().rollback();
			throw new DBConnectionException(e.getMessage(),e.getCause());
		}finally {
			// session.getTransaction().commit();
			session.close();
		}
	}

	@Override
	public void update(ProductoDTO dto) {
		Session session = this.factory.getCurrentSession();
		session.getTransaction().begin();
		Producto producto = session.get(Producto.class, dto.getId());
		TipoProducto tp = session.get(TipoProducto.class, dto.getTipoProducto().getId());

		//actualizamos
		producto.setPrecio(dto.getPrecio());
		producto.setTitulo(dto.getTitulo());
		producto.setTipoProducto(tp);
		//y mas campo
		
		session.persist(producto);//TODO: VER PORQUE NO ACTUALIZA
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void delete(Long id) {
		Session session = this.factory.getCurrentSession();
		session.getTransaction().begin();
		
		Producto producto = session.get(Producto.class, id);
		
		//delete from tabla where id = 1
		
		session.remove(producto);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public List<ProductoDTO> findAll() {
		//escribiendo query / consultas usando hibernate
		//sql > select * from tabla where campo = valor...
		
		Session session = this.factory.getCurrentSession();
		session.getTransaction().begin();
		String hql = "Select p from " + Producto.class.getName() + " p";
		Query<Producto> query = session.createQuery(hql, Producto.class); // TODO: ver que metodo usar
		
		List<Producto> productos = query.getResultList();
	
		session.getTransaction().commit();			
		session.close();

		//aplica lambdas
		return productos.stream()
			.map(p -> new ProductoDTO(p.getId(), p.getTitulo(), p.getCodigo(), p.getPrecio(),p.getTipoProducto()))
			.collect(Collectors.toList());		
	}

	@Override
	public ProductoDTO getByCodigo(String codigo) {
		
		Session session = this.factory.getCurrentSession();
		session.getTransaction().begin();
		String hql = "select p from " + Producto.class.getName() + " p where p.codigo=:codigo";
		Query<Producto> query = session.createQuery(hql, Producto.class);
		query.setParameter("codigo", codigo);
		Producto p = query.uniqueResult();//objeto o nulo
		
		session.close();
		
		if(p != null) {
			ProductoDTO dto = new ProductoDTO(p.getId(), p.getTitulo(), p.getPrecio());
			dto.setCodigo(p.getCodigo());
			dto.setTipoProducto(p.getTipoProducto());
			return dto;
		}
		return null;
	}

	@Override
	public List<TipoProducto> findTipoProductos() throws GenericException {
		Session session = this.factory.getCurrentSession();
		
		List<TipoProducto> tipoProductos = new ArrayList<>();
		
		try {
			
			session.getTransaction().begin();
			
			//HQL
			String hql = "Select p from " + TipoProducto.class.getName() + " p" ;
			
			Query<TipoProducto> query = session.createQuery(hql,TipoProducto.class);
				
			tipoProductos = query.getResultList();
			
			session.getTransaction().commit();			
		}catch (Exception e) {
			session.getTransaction().rollback();
			throw new GenericException(e.getMessage(), e);			
		} finally {
			session.close();
		}
		
		return tipoProductos;
	}
}
