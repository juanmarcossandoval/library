package com.bibliotek.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bibliotek.library.entities.Ciudad;
import com.bibliotek.library.exceptions.BadRequestException;
import com.bibliotek.library.exceptions.NotFoundException;
import com.bibliotek.library.repositories.CiudadRepository;

import jakarta.transaction.Transactional;
import utils.StringUtils;

@Service
@Transactional
public class CiudadService {
	private CiudadRepository ciudadRepo;
	private ProvinciaService provinciaServ;
	
	public CiudadService (CiudadRepository ciudadRepo, ProvinciaService provinciaServ ) {
		this.ciudadRepo = ciudadRepo;
		this.provinciaServ = provinciaServ;
	}
	
	public Ciudad crearNuevaC(Ciudad nuevo) throws NotFoundException {
		if(StringUtils.Check(nuevo.getNombre())) {
			return null;
		}
		List<Ciudad>encontrados = buscarPorNombre(nuevo.getNombre());
		this.buscarPorId(nuevo.getId_ciudad());
		if(encontrados.isEmpty()) {
			return this.ciudadRepo.save(nuevo);
		}
		return null;
	}
	
	public List <Ciudad> buscarPorNombre(String nombre) {
		return this.ciudadRepo.findByName (nombre);
	}
	
	public Ciudad buscarPorId (Long id) throws NotFoundException {
		Optional<Ciudad> buscado = this.ciudadRepo.findById(id);
		return buscado.orElseThrow(()-> new NotFoundException("no hay ciudades con ese id"));
	}
	
	public void eliminarPorId (Long id) {
		this.ciudadRepo.deleteById(id);
	}
	
	public List<Ciudad> listarTodos(){
		return this.ciudadRepo.findAll();
	}
	
	public Ciudad actualizar(Ciudad actualizada) throws NotFoundException, BadRequestException {
		//valida que esten todos los datos de la ciudad
		if(invalidCity(actualizada)) return null;
		//valida que esten todos los datos de la provincia hija del objeto
		if(provinciaServ.invalidProv(actualizada.getProvincia())) return null;
		//como es un update valida que exista una ciudad con ese ID
		Ciudad existe = this.buscarPorId(actualizada.getId_ciudad());
		if (existe == null) return null;
		// si no hay nada que actualizar volvemos
		if (existe.equals(actualizada)) return actualizada;
		// chequeamos que no halla otra ciudad con el mismo nombre
		if (invalidName(actualizada)) return null;
		// chequeamos que no halla otra provincia con el mismo nombre
		if (this.provinciaServ.invalidName(actualizada.getProvincia())) return null;
		return this.ciudadRepo.save(actualizada);
	}
	
	public boolean invalidCity(Ciudad ciudad) throws BadRequestException {
		if (ciudad == null) return true;
		if (ciudad.getId_ciudad() == null) 
			throw new BadRequestException("El id de la ciudad no puede ser nulo");
		if (StringUtils.Check(ciudad.getNombre())) return true;
		return false;
	}
	
	public boolean invalidName(Ciudad actualizada) {
		//chequeamos el nombre ahora para que no se repita
		List<Ciudad> encontradas = this.ciudadRepo.findByName(actualizada.getNombre());
		// si encontro mas de una hay una inconsistencia en la base de datos
		if (encontradas.size() > 1) return true;
		if (encontradas.isEmpty()) return false;
		return encontradas.get(0).getId_ciudad()!= actualizada.getId_ciudad();
	}
}
