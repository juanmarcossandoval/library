package com.bibliotek.library.controllers;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bibliotek.library.entities.Provincia;
import com.bibliotek.library.services.ProvinciaService;

@Controller
@RequestMapping("/Provincias")

public class ProvinciaController {
	
	private ProvinciaService provinciaService;
	
	public ProvinciaController(ProvinciaService provinciaService) {
		this.provinciaService = provinciaService;
	}
	
	@PostMapping
	public ResponseEntity<?> postOne(@RequestBody Provincia nuevo){
		Provincia nuevop = provinciaService.crearNuevaP(nuevo);
		if (nuevop == null) {
			return new ResponseEntity<>("El nombre no puede estar vacio, ni estar en blanco, o no puede repetirse la Provincia", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(nuevop, HttpStatus.CREATED);
	}
	
	@GetMapping("/filter")
	public ResponseEntity <?> findByName(@RequestParam String nombre){
		List<Provincia> encontrados = provinciaService.buscarPorNombre(nombre);
		return new ResponseEntity <>(encontrados, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		List<Provincia> lista = provinciaService.listarTodos();
		return new ResponseEntity<>(lista,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <?> findOne(@PathVariable Long id){
		Provincia encontrado = provinciaService.buscarPorId(id);
		if(encontrado == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(encontrado,HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<?>putOne(@RequestBody Provincia nuevo){
		Provincia actualizado = provinciaService.crearNuevaP(nuevo);
		if(actualizado == null) {
			return new ResponseEntity<>
			("El nombre de la Provincia no puede repetirse, estar vacio o en blanco", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(actualizado,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable Long id){
		provinciaService.eliminarPorid(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<?>patchOne(@RequestBody Provincia actualizado){
		Provincia actual = provinciaService.crearNuevaP(actualizado);
		if(actual == null) {
			return new ResponseEntity <>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity <> (actual, HttpStatus.OK);
	}
}
