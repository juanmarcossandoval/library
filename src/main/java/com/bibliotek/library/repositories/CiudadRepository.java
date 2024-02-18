package com.bibliotek.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bibliotek.library.entities.Ciudad;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

		@Query(value = "SELECT * FROM CIUDADES c WHERE c.NOMBRE = :ciudad",nativeQuery = true)
		public List<Ciudad> findByName(@Param(value = "ciudad") String nombre);
}
