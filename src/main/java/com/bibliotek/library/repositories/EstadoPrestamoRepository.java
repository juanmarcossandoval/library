package com.bibliotek.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bibliotek.library.entities.EstadoPrestamo;

@Repository
public interface EstadoPrestamoRepository extends JpaRepository <EstadoPrestamo,Long> {

}
