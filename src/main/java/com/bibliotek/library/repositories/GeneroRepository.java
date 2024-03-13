package com.bibliotek.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bibliotek.library.entities.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero,Long> {

}
