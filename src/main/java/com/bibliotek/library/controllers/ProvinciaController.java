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

import com.bibliotek.library.entities.Provincia;
import com.bibliotek.library.exceptions.BadRequestException;
import com.bibliotek.library.exceptions.NotFoundException;
import com.bibliotek.library.services.ProvinciaService;

@Controller
@RequestMapping("/Provincias")

public class ProvinciaController {
	
	private ProvinciaService provinciaService;
	
	public ProvinciaController(ProvinciaService provinciaService) {
		this.provinciaService = provinciaService;
	}
	
	@PostMapping
	public ResponseEntity<?> postOne(@RequestBody Provincia nuevo) throws BadRequestException{
		Provincia nuevop = this.provinciaService.crearNuevaP(nuevo);
		return new ResponseEntity<>(nuevop, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity <?> findByName(@RequestParam Optional<String> nombre){
		List<Provincia> encontrados = this.provinciaService.filtrar(nombre);		
		return new ResponseEntity <>(encontrados, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <?> findOne(@PathVariable Long id) throws NotFoundException {
		Provincia encontrado = this.provinciaService.buscarPorId(id);
		return new ResponseEntity<>(encontrado,HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<?>putOne(@RequestBody Provincia nuevo) throws BadRequestException, NotFoundException{
		Provincia actualizado = this.provinciaService.actualizar(nuevo);
		return new ResponseEntity<>(actualizado,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable Long id){
		this.provinciaService.eliminarPorid(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
