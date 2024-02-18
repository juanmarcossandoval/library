package com.bibliotek.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bibliotek.library.entities.Provincia;
import com.bibliotek.library.repositories.ProvinciaRepository;

@Service
public class ProvinciaService {
	private ProvinciaRepository provinciaRepository;
	
	public ProvinciaService (ProvinciaRepository provincia) {
		this.provinciaRepository = provincia;
	}
	
	public Provincia crearNuevaP(Provincia nuevo) {
		if(nuevo.getNombre() == null || nuevo.getNombre().isEmpty() || nuevo.getNombre().isBlank()) {
			return null;
		}
		List<Provincia> encontrados = buscarPorNombre(nuevo.getNombre());
		if (encontrados.isEmpty()) {
			Provincia nuevaP = this.provinciaRepository.save(nuevo);
			return nuevaP;
		}
		return null;
	}

	public List <Provincia> buscarPorNombre(String nombre) {
		return this.provinciaRepository.findByName(nombre);
	}
	
	public Provincia buscarPorId (Long id) {
		Optional <Provincia> buscado = this.provinciaRepository.findById(id);
		return buscado.orElse(null);
	}	
	
	public void eliminarPorid (Long id) {
		this.provinciaRepository.deleteById(id);
	}
	
	public List<Provincia> listarTodos(){
		return this.provinciaRepository.findAll();
	}
}
