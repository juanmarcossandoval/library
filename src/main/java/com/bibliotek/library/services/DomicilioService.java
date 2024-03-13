package com.bibliotek.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bibliotek.library.entities.Ciudad;
import com.bibliotek.library.entities.Domicilio;
import com.bibliotek.library.exceptions.BadRequestException;
import com.bibliotek.library.exceptions.NotFoundException;
import com.bibliotek.library.repositories.DomicilioRepository;

import utils.StringUtils;

@Service
public class DomicilioService {
	private final DomicilioRepository domiRepo; //Al ser un singleton, por buena practica debera ser una constante(final)
	private final CiudadService ciudadService;
	
	public DomicilioService (DomicilioRepository domiRepo, CiudadService ciudadService) {
		//declaro el constructor de domicilio service 
		//nunca voy a hacer un new de domicilio service
		//Spring por el stereotipo de servicio, trata la clase como un singleton
		//Los Servicios , los repositorios y los controladores al ser singletons 
		//tiene que ser inyectados.
		
		this.domiRepo = domiRepo;
		this.ciudadService = ciudadService;
	}
	
	public Domicilio crearNuevod(Domicilio nuevo) throws BadRequestException, NotFoundException {
		if(null != nuevo.getId_domicilio()) {
			Optional<Domicilio> buscado = this.domiRepo.findById(nuevo.getId_domicilio());
			if(buscado.isPresent())
				throw new BadRequestException("Ya Existe un Domicilio con este ID");
		}
		if(null == nuevo.getDireccion())
			throw new BadRequestException("La Direccion no puede estar nula");
		if(StringUtils.Check(nuevo.getDireccion()))
			throw new BadRequestException("La Direccion no puede estar vacia o en blanco");
		this.ciudadService.invalidCityData(nuevo.getCiudad());
		this.ciudadService.isValidCity(nuevo.getCiudad());
		return this.domiRepo.save(nuevo);
	}
	
	public List <Domicilio> buscarPorDireccion(String domi){
		return this.domiRepo.findByDireccion(domi);
	}
	
	public Domicilio buscarPorId(Long id) throws NotFoundException {
		Optional<Domicilio> buscado = this.domiRepo.findById(id);
		return buscado.orElseThrow(()-> new NotFoundException("No Hay Domicilio con ese Id"));
	}
	
	public void eliminarPorId (Long id) {
		this.domiRepo.deleteById(id);
	}
	
	public List<Domicilio> listarTodos(){
		return this.domiRepo.findAll();
	}
	
	public void invalidDomData(Domicilio domi) throws BadRequestException {
		if (domi == null)
			throw new BadRequestException("El Domicilio deberia contener algu dato");
		if (null == domi.getId_domicilio())
			throw new BadRequestException("El id del Domicilio no puede ser nulo");
		if (null == domi.getDireccion())
			throw new BadRequestException("La Direccion no puede ser nula");
		if (StringUtils.Check(domi.getDireccion()))
			throw new BadRequestException("La Direccion no puede esta en blanco ni vacia");
	}
	
	public Domicilio actualizar (Domicilio actualizar) throws BadRequestException, NotFoundException {
		this.invalidDomData(actualizar);
		this.ciudadService.invalidCityData(actualizar.getCiudad());
		Ciudad existe = this.ciudadService.buscarPorId(actualizar.getCiudad().getId_ciudad());
		if(!existe.equals(actualizar.getCiudad()))
			throw new BadRequestException("Los datos de la ciudad no se corresponden con los de la BDD");
		Domicilio check = this.buscarPorId(actualizar.getId_domicilio());
		if (check.equals(actualizar))
			throw new BadRequestException("Ningun dato para actualizar");
		return this.domiRepo.save(actualizar);
	}
	
	public List<Domicilio> filtrar(Optional<String> direccion){
		if (direccion.isPresent())
			return this.buscarPorDireccion(direccion.get());
		return this.listarTodos();
	}
 	
}
