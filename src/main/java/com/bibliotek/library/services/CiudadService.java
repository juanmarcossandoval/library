package com.bibliotek.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bibliotek.library.entities.Ciudad;
import com.bibliotek.library.exceptions.BadRequestException;
import com.bibliotek.library.exceptions.NotFoundException;
import com.bibliotek.library.repositories.CiudadRepository;

import utils.StringUtils;

@Service
public class CiudadService {
	private CiudadRepository ciudadRepo;
	private ProvinciaService provinciaServ;
	
	public CiudadService (CiudadRepository ciudadRepo, ProvinciaService provinciaServ ) {
		this.ciudadRepo = ciudadRepo;
		this.provinciaServ = provinciaServ;
	}
	
	public Ciudad crearNuevaC(Ciudad nuevo) throws NotFoundException, BadRequestException {
		if(null != nuevo.getId_ciudad()) {//si viene un id, checkeamos que no este en uso.
			//si el id esta en uso, el metodo nos actualiza los datos y no deberia.
            Optional<Ciudad> buscado = this.ciudadRepo.findById(nuevo.getId_ciudad());
            if (buscado.isPresent())
                throw new BadRequestException("Ya existe una ciudad con ese id");
        }
        if (null == nuevo.getNombre())
            throw new BadRequestException("El nombre de la Ciudad no puede ser nulo");
        if(StringUtils.Check(nuevo.getNombre())) 
            throw new BadRequestException("El nombre de la Ciudad no puede estar vacio o en blanco");
        List<Ciudad>encontrados = this.buscarPorNombre(nuevo.getNombre());
        if(!encontrados.isEmpty()) //si la lista no esta vacia, entra en el throw.
            throw new BadRequestException("Ya existe una ciudad con ese nombre.");
        this.provinciaServ.invalidProvData(nuevo.getProvincia());
        this.provinciaServ.isValidProv(nuevo.getProvincia());
        return this.ciudadRepo.save(nuevo);
	}
	
	public List <Ciudad> buscarPorNombre(String nombre) {
		return this.ciudadRepo.findByName(nombre);
	}
	
	public Ciudad buscarPorId (Long id) throws NotFoundException {
		Optional<Ciudad> buscado = this.ciudadRepo.findById(id);
		return buscado.orElseThrow(()-> new NotFoundException("No hay ciudades con ese id."));
	}
	
	public void eliminarPorId (Long id) {
		this.ciudadRepo.deleteById(id);
	}
	
	public List<Ciudad> listarTodos(){
		return this.ciudadRepo.findAll();
	}
	
	public Ciudad actualizar(Ciudad actualizada) throws NotFoundException, BadRequestException {
		//valida que esten todos los datos de la ciudad
		this.invalidCityData(actualizada);
		//valida que esten todos los datos de la provincia hija del objeto
		this.provinciaServ.invalidProvData(actualizada.getProvincia());
		//como es un update valida que exista una ciudad con ese ID
		Ciudad existe = this.buscarPorId(actualizada.getId_ciudad());
		if (existe == null) 
			throw new BadRequestException("No existe una Ciudad con ese Id.");
		// si no hay nada que actualizar volvemos
		if (existe.equals(actualizada))
			throw new BadRequestException("Ningun dato para actualizar.");
		// chequeamos que no halla otra ciudad con el mismo nombre
		if (invalidName(actualizada)) 
			throw new BadRequestException("El nombre de la Ciudad ya existe.");
		this.provinciaServ.isValidProv(actualizada.getProvincia());
		return this.ciudadRepo.save(actualizada);
	}
	
	public void invalidCityData(Ciudad ciudad) throws BadRequestException {
		//que la ciudad no este vacia
		if (ciudad == null) 
			throw new BadRequestException("La ciudad deberia contener algun dato");
		// la ciudad debe tener un id
		if ( null == ciudad.getId_ciudad()) 
			throw new BadRequestException("El id de la ciudad no puede ser nulo");
		if(null == ciudad.getNombre())
			throw new BadRequestException("El nombre de la Ciudad no puede estar vacio.");
		// porque el nombre vino vacio
		if (StringUtils.Check(ciudad.getNombre()))
			throw new BadRequestException("El nombre de la Ciudad no puede ser nula, vacia, o en blanco. ");
		
	}
	
	public boolean invalidName(Ciudad actualizada) {
		//chequeamos el nombre ahora para que no se repita
		List<Ciudad> encontradas = this.ciudadRepo.findByName(actualizada.getNombre());
		// si encontro mas de una hay una inconsistencia en la base de datos
		if (encontradas.size() > 1) return true;
		if (encontradas.isEmpty()) return false;
		return encontradas.get(0).getId_ciudad()!= actualizada.getId_ciudad();
	}
	
	public void isValidCity(Ciudad city) throws NotFoundException, BadRequestException {
		Ciudad encontrada = this.buscarPorId(city.getId_ciudad());
		if(!encontrada.equals(city))
			throw new BadRequestException("Los datos de la ciudad y provincia no se corresponden con los guardados en la Base de Datos");
	}
	
	public List<Ciudad> filtrar(Optional<String> nombre){
		if (nombre.isPresent())
			return this.buscarPorNombre(nombre.get());
		return this.listarTodos();
	}
}
