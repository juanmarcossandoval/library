package com.bibliotek.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bibliotek.library.entities.Provincia;
import com.bibliotek.library.repositories.ProvinciaRepository;

import utils.StringUtils;

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
	
	public List<Provincia> filtrar(Optional<String> nombre){
		if(nombre.isPresent()) {
			return this.buscarPorNombre(nombre.get());
		}
		return this.listarTodos();
	}
	
	public Provincia actualizar(Provincia actualizada) {
		if(invalidProv(actualizada))return null;
		Provincia existente = this.buscarPorId(actualizada.getId_provincia());
		if (existente == null) return null;
		if(existente.equals(actualizada)) return actualizada;
		if(invalidName(actualizada)) return null;
		return this.provinciaRepository.save(actualizada);
	}
	
	public boolean invalidProv(Provincia prov) {
		if (prov == null) return true;
		if (prov.getId_provincia() == null) return true;
		if (StringUtils.Check(prov.getNombre())) return true;
		return false;
	}
	
	public boolean invalidName(Provincia actualizada) {
		//chequeamos el nombre ahora para que no se repita
		List<Provincia> encontradas = this.provinciaRepository.findByName(actualizada.getNombre());
		// si encontro mas de una hay una inconsistencia en la base de datos
		if (encontradas.size() > 1) return true;
		if (encontradas.isEmpty()) return false;
		return encontradas.get(0).getId_provincia()!= actualizada.getId_provincia();
	}
	
}
