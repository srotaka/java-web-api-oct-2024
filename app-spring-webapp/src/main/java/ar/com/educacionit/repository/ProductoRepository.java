package ar.com.educacionit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.educacionit.domain.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
	
}
