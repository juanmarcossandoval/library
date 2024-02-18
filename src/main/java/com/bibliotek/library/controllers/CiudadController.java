package com.bibliotek.library.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bibliotek.library.entities.Ciudad;
import com.bibliotek.library.services.CiudadService;

@Controller
@RequestMapping("/Ciudades")

public class CiudadController {

	private CiudadService ciudadService;
		
	public CiudadController(CiudadService ciudadService) {
			this.ciudadService = ciudadService;
}
		
@PostMapping
public ResponseEntity <?> postOne(@RequestBody Ciudad nuevo){
	Ciudad nuevaC = ciudadService.crearNuevaC(nuevo);
	if(nuevaC == null) {
		return new ResponseEntity<>("El nombre no puede estar vacio,ni estar en blanco, o no puede repetirse la ciudad", HttpStatus.BAD_REQUEST); 
	}
	return new ResponseEntity<>(nuevaC,HttpStatus.CREATED);
}

@GetMapping("/filter")
public ResponseEntity <?> findByName(@RequestParam String nombre){
	List<Ciudad> encontrados = ciudadService.buscarPorNombre(nombre);
	return new ResponseEntity <>(encontrados,HttpStatus.OK);
}

@GetMapping
public ResponseEntity <?> getAll(){
	List<Ciudad> lista = ciudadService.listarTodos();
	return new ResponseEntity<>(lista, HttpStatus.OK);
}

@GetMapping("/{id}")
public ResponseEntity <?> findOne(@PathVariable Long id){
	Ciudad encontrado = ciudadService.buscarPorId(id);
	if(encontrado == null) {
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	return new ResponseEntity<>(encontrado,HttpStatus.OK);
}

@PutMapping //actualiza un recurso nuevo completo (cambio el parametro por actual, por actualcompleto)
public ResponseEntity<?>putOne(@RequestBody Ciudad actualcompleto){
	Ciudad actualizado = ciudadService.crearNuevaC(actualcompleto);
	if(actualizado == null) {
		return new ResponseEntity<>
		("El nombre de la Ciudad no puede repetirse, estar vacio o en blanco", HttpStatus.BAD_REQUEST);
	}
	return new ResponseEntity<>(actualizado,HttpStatus.OK);
}

@PatchMapping
public ResponseEntity<?>patchOne(@RequestBody Ciudad actualizado){
	Ciudad actual = ciudadService.actualizar(actualizado);
	if(actual == null) {
		return new ResponseEntity <> (HttpStatus.BAD_REQUEST);
	}
	return new ResponseEntity <> (actual, HttpStatus.OK);
}
}
