package com.bibliotek.library.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.bibliotek.library.entities.Domicilio;

@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio, Long>{
	
	@Query(value = "SELECT * FROM DOMICILIOS d WHERE d.DIRECCION LIKE %:direccion% ",nativeQuery = true )
	public List<Domicilio> findByDireccion(@Param(value = "direccion")String direccion);
}
