package com.bibliotek.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bibliotek.library.entities.Ciudad;
import com.bibliotek.library.entities.Provincia;
import com.bibliotek.library.repositories.CiudadRepository;
import com.bibliotek.library.repositories.ProvinciaRepository;

import utils.StringUtils;

@Service
public class CiudadService {
	private CiudadRepository ciudadRepository;
	private ProvinciaRepository provinciaRepository;
	
	public CiudadService (CiudadRepository ciudad, ProvinciaRepository provincia ) {
		this.ciudadRepository = ciudad;
		this.provinciaRepository = provincia;
	}
	
	public Ciudad crearNuevaC(Ciudad nuevo) {
		if(StringUtils.Check(nuevo.getNombre())) {
			return null;
		}
		List<Ciudad>encontrados = buscarPorNombre(nuevo.getNombre());
		if(encontrados.isEmpty()) {
			Ciudad nuevaC = this.ciudadRepository.save(nuevo);
			return nuevaC;
		}
		return null;
	}
	
	public List <Ciudad> buscarPorNombre(String nombre) {
		return this.ciudadRepository.findByName (nombre);
	}
	
	public Ciudad buscarPorId (Long id) {
		Optional<Ciudad> buscado = this.ciudadRepository.findById(id);
		return buscado.orElse(null);
	}
	
	public void eliminarPorId (Long id) {
		this.ciudadRepository.deleteById(id);
	}
	
	public List<Ciudad> listarTodos(){
		return this.ciudadRepository.findAll();
	}
	
	public Ciudad actualizar(Ciudad actualizado ) {
		if(StringUtils.Check(actualizado.getNombre())){
			return null;
		}
		if(actualizado.getProvincia() == null)
			return null;
		if(StringUtils.Check(actualizado.getProvincia().getNombre()))
			return null;
		if(actualizado.getId_ciudad() == null || actualizado.getProvincia().getId_provincia() == null)
			return null;
		List<Provincia> encontradas = this.provinciaRepository.findByName(actualizado.getProvincia().getNombre());
		if(encontradas.isEmpty())
			return null;
		Ciudad aux = new Ciudad();
		aux.setProvincia(encontradas.get(0));
		List<Ciudad> buscadas = this.ciudadRepository.findByName(actualizado.getNombre());
			if(!buscadas.isEmpty()) {
				if(buscadas.get(0).getId_ciudad() != actualizado.getId_ciudad()) {
					return null;
				}else {
					aux.setNombre(actualizado.getNombre());
					aux.setId_ciudad(actualizado.getId_ciudad());
					return this.ciudadRepository.save(aux);
				}
			
			}
			aux.setNombre(actualizado.getNombre());
			aux.setId_ciudad(actualizado.getId_ciudad());
			return this.ciudadRepository.save(aux);
	}
}
