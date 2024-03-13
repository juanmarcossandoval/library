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

import com.bibliotek.library.entities.Domicilio;
import com.bibliotek.library.exceptions.BadRequestException;
import com.bibliotek.library.exceptions.NotFoundException;
import com.bibliotek.library.services.DomicilioService;

@Controller
@RequestMapping("/Domicilios")

public class DomicilioController {
	private final DomicilioService domicilioService;
	
	public DomicilioController(DomicilioService domicilioService){
		this.domicilioService = domicilioService;
	}
	
	@PostMapping
	public ResponseEntity<?> postOne(@RequestBody Domicilio nuevo) throws BadRequestException, NotFoundException{
		Domicilio nuevoD = this.domicilioService.crearNuevod(nuevo);
		return new ResponseEntity<>(nuevoD, HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<?> putOne(@RequestBody Domicilio actualCompleto) throws BadRequestException, NotFoundException{
		Domicilio actualizada = this.domicilioService.actualizar(actualCompleto); 
		return new ResponseEntity<>(actualizada, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(@RequestParam Optional<String>direccion){
		List <Domicilio> lista = this.domicilioService.filtrar(direccion);
		return new ResponseEntity<>(lista,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findOne (@PathVariable Long id) throws NotFoundException{
		Domicilio encontrado = this.domicilioService.buscarPorId(id);
		return new ResponseEntity<>(encontrado, HttpStatus.OK);
	}
	
	@DeleteMapping ("/{id}")
	public ResponseEntity <?>deleteOne(@PathVariable Long id) {
		this.domicilioService.eliminarPorId(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
