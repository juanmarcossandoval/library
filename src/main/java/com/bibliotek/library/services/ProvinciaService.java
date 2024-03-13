package com.bibliotek.library.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.bibliotek.library.entities.Provincia;
import com.bibliotek.library.exceptions.BadRequestException;
import com.bibliotek.library.exceptions.NotFoundException;
import com.bibliotek.library.repositories.ProvinciaRepository;
import utils.StringUtils;

@Service
public class ProvinciaService {
	private ProvinciaRepository provinciaRepository;
	
	public ProvinciaService (ProvinciaRepository provincia) {
		this.provinciaRepository = provincia;
	}
	
	public Provincia crearNuevaP(Provincia nuevo) throws BadRequestException {
		if(null != nuevo.getId_provincia()) {
			Optional<Provincia>buscado = this.provinciaRepository.findById(nuevo.getId_provincia());
			if(buscado.isPresent())
				throw new BadRequestException("ya existe una Provincia con ese Id.");
		}
		if (null == nuevo.getNombre())
			throw new BadRequestException("El nombre de la Provincia no puede ser nulo");
		if(StringUtils.Check(nuevo.getNombre()))
			throw new BadRequestException("El nombre de la Provincia no puede estar vacio o en blanco .");
		List<Provincia> encontrados = this.buscarPorNombre(nuevo.getNombre());
		if (!encontrados.isEmpty()) 
			throw new BadRequestException("Ya existe una Provincia con ese nombre");
		return this.provinciaRepository.save(nuevo);
	}

	public List <Provincia> buscarPorNombre(String nombre) {
		return this.provinciaRepository.findByName(nombre);
	}
	
	public Provincia buscarPorId (Long id) throws NotFoundException {
		Optional <Provincia> buscado = this.provinciaRepository.findById(id);
		return buscado.orElseThrow(
					() -> new NotFoundException("No se encontro una provincia con este ID.")
				);
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
	
	public Provincia actualizar(Provincia actualizada) throws BadRequestException, NotFoundException {
		invalidProvData(actualizada);
		Provincia existente = this.buscarPorId(actualizada.getId_provincia());
		if (existente == null) 
			throw new BadRequestException("No Existe una Provincia con ese Id");
		if(existente.equals(actualizada)) 
			throw new BadRequestException("Ningun Dato para actualizar");
		if(invalidName(actualizada))
			throw new BadRequestException("El nombre de la Provincia ya existe");
		return this.provinciaRepository.save(actualizada);
	}
	
	public void invalidProvData(Provincia prov) throws BadRequestException {
		if (null == prov)
			throw new BadRequestException("La Provincia deben tener al menos un dato.");
		if (prov.getId_provincia() == null) 
			throw new BadRequestException("El Id de la Provincia no puede esta vacio.");
		if (null == prov.getNombre())
			throw new BadRequestException("El nombre de la Provincia no puede estar nulo.");
		if (StringUtils.Check(prov.getNombre())) 
			throw new BadRequestException("El nombre de la Provincia no puede estar vacio o en blanco.");
	}
	
	public boolean invalidName(Provincia actualizada) {
		//chequeamos el nombre ahora para que no se repita
		List<Provincia> encontradas = this.provinciaRepository.findByName(actualizada.getNombre());
		// si encontro mas de una hay una inconsistencia en la base de datos
		if (encontradas.size() > 1) return true;
		if (encontradas.isEmpty()) return false;
		return encontradas.get(0).getId_provincia()!= actualizada.getId_provincia();
	}
	
	public void isValidProv(Provincia prov) throws BadRequestException, NotFoundException {
		//checkeamos que los datos de la provincia se correspondan con los de la bdd.
		Provincia encontrada = this.buscarPorId(prov.getId_provincia());
		if(encontrada.equals(prov)) {
			throw new BadRequestException("Los datos de Provincia guardados para este ID de Provincia, no se corresponden con los de la peticion.");
		}
	}
}
