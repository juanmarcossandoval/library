package com.bibliotek.library.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bibliotek.library.entities.Ciudad;
import com.bibliotek.library.exceptions.BadRequestException;
import com.bibliotek.library.exceptions.NotFoundException;
import com.bibliotek.library.services.CiudadService;

@Controller
@RequestMapping("/Ciudades")

public class CiudadController {

	private CiudadService ciudadService;

	public CiudadController(CiudadService ciudadService) {
		this.ciudadService = ciudadService;
	}

	@PostMapping
	public ResponseEntity<?> postOne(@RequestBody Ciudad nuevo) throws NotFoundException, BadRequestException {
		Ciudad nuevaC = this.ciudadService.crearNuevaC(nuevo);
		return new ResponseEntity<>(nuevaC, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<?> getAll(@RequestParam Optional <String> nombre) {
		List<Ciudad> lista = this.ciudadService.filtrar(nombre);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findOne(@PathVariable Long id) throws NotFoundException {
		Ciudad encontrado = this.ciudadService.buscarPorId(id);
		return new ResponseEntity<>(encontrado, HttpStatus.OK);
	}

	@PutMapping // actualiza un recurso nuevo completo 
				// (cambio el parametro por actual, por actualcompleto)
	public ResponseEntity<?> putOne(@RequestBody Ciudad actualcompleto) throws NotFoundException, BadRequestException {
		Ciudad actualizado = this.ciudadService.actualizar(actualcompleto);
		return new ResponseEntity<>(actualizado, HttpStatus.OK);
	}
	
	@DeleteMapping ("/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable Long id){
		this.ciudadService.eliminarPorId(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
		
	
}
