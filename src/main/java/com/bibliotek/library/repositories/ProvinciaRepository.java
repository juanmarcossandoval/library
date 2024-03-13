package com.bibliotek.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bibliotek.library.entities.Provincia;

@Repository
public interface ProvinciaRepository extends JpaRepository <Provincia, Long>{
	
	@Query(value = "SELECT * FROM PROVINCIAS p WHERE p.NOMBRE = :provincia",nativeQuery = true)
	public List <Provincia> findByName(@Param (value = "provincia") String nombre);
	
}
