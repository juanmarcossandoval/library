package com.bibliotek.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bibliotek.library.entities.Editorial;

@Repository
public interface EditorialRepository extends JpaRepository <Editorial, Long >{
	
	@Query(value = "SELECT * FROM EDITORIALES e " 
			+ "WHERE e.NOMBRE = :nombre" , nativeQuery = true)
	List <Editorial>findByName(@Param (value = "nombre")String nombre);
	
}
