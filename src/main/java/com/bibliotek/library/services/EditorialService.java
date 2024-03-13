package com.bibliotek.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bibliotek.library.entities.Editorial;
import com.bibliotek.library.repositories.EditorialRepository;

@Service
public class EditorialService {
	
	private EditorialRepository editorialRepository;
	
	public EditorialService (EditorialRepository editorial){
		this.editorialRepository = editorial;
	}
	
	public Editorial guardar (Editorial nuevo) {
		if (nuevo.getNombre() == null || nuevo.getNombre().isEmpty() || nuevo.getNombre().isBlank()) {
			return null;
		}
		List<Editorial> encontrados = buscarPorNombre(nuevo.getNombre());
		if(encontrados.isEmpty() ) {
			Editorial nuevoe = this.editorialRepository.save(nuevo);
			return nuevoe;
		}
		return null;
	}
	
	public Editorial buscarPorId (Long id) {
		Optional<Editorial> buscado = this.editorialRepository.findById(id);
		return buscado.orElse(null);
	}
	
	public List <Editorial> buscarPorNombre(String nombre) {
		return this.editorialRepository.findByName(nombre);
	}
	
	public Editorial actualizar (Editorial actualizado) {
		if(actualizado.getId_editorial() == null) {
			return null;
		}
		if (actualizado.getNombre() == null || actualizado.getNombre().isEmpty() || actualizado.getNombre().isBlank()) {
			return null;
		}else {
			Editorial actual = this.buscarPorId(actualizado.getId_editorial());
			if (actual == null)
				return null;
			actual.setNombre(actualizado.getNombre());
			return this.guardar(actual);
		}
	}
	
	public void eliminarPorId (Long id) {
		this.editorialRepository.deleteById(id);
	}
	
	public List <Editorial> listarTodos(){
		return this.editorialRepository.findAll();		
	}
}
