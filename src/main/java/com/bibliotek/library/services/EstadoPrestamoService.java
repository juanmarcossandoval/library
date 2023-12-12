package com.bibliotek.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bibliotek.library.entities.EstadoPrestamo;
import com.bibliotek.library.repositories.EstadoPrestamoRepository;

@Service
public class EstadoPrestamoService {

	private EstadoPrestamoRepository estadoPrestamoRepository;

	public EstadoPrestamoService (EstadoPrestamoRepository prestamo) {
		this.estadoPrestamoRepository = prestamo;
	}
	
	public EstadoPrestamo guardar (EstadoPrestamo nuevo) {
		EstadoPrestamo nuevop = this.estadoPrestamoRepository.save(nuevo);
		return nuevop;
	}
	
	public EstadoPrestamo buscarPorId (Long id) {
	Optional<EstadoPrestamo> buscado = this.estadoPrestamoRepository.findById(id);
		return buscado.orElse(null);
	}
	
	public void eliminarPorId (Long id) {
		this.estadoPrestamoRepository.deleteById(id);
	}
	
	public List <EstadoPrestamo> listarTodos (){
		return this.estadoPrestamoRepository.findAll();
	}

	public EstadoPrestamo actualizar (EstadoPrestamo actualizado) {
		if (actualizado.getId_estado() == null) {
			return null;
		}
		if (actualizado.getEstado() == null || actualizado.getEstado().isEmpty() || actualizado.getEstado().isBlank()) {
			return null;
		}else {
			EstadoPrestamo actual = this.buscarPorId(actualizado.getId_estado());
			if (actual == null) 
				return null;
			actual.setEstado(actualizado.getEstado());
				return this.guardar(actual);
		}
	}
}

