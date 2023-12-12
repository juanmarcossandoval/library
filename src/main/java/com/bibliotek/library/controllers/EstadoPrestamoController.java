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
import com.bibliotek.library.entities.EstadoPrestamo;
import com.bibliotek.library.services.EstadoPrestamoService;

@Controller
@RequestMapping("/EstadosPrestamos")
public class EstadoPrestamoController {

	private EstadoPrestamoService prestamoService;

	public EstadoPrestamoController(EstadoPrestamoService prestamoService) {
		this.prestamoService = prestamoService;
	}

	@PostMapping
	public ResponseEntity<?> postone(@RequestBody EstadoPrestamo nuevo) {
		EstadoPrestamo nuevop = prestamoService.guardar(nuevo);
		return new ResponseEntity<>(nuevop, HttpStatus.CREATED);
	}
	@GetMapping
	public ResponseEntity <?> getAll(){
		List<EstadoPrestamo> lista = prestamoService.listarTodos();
		return new ResponseEntity<>(lista,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <?> finOne(@PathVariable Long id){
		EstadoPrestamo encontrado = prestamoService.buscarPorId(id);
		if (encontrado == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(encontrado,HttpStatus.OK);
	}	
	@PutMapping
	public ResponseEntity<?> putOne(@RequestBody EstadoPrestamo nuevo){
		EstadoPrestamo actualizado = prestamoService.guardar(nuevo);
		return new ResponseEntity<> (actualizado,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable Long id){
		prestamoService.eliminarPorId(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<?> patchOne(@RequestBody EstadoPrestamo actualizado){
		EstadoPrestamo actual = prestamoService.actualizar(actualizado);
		if (actual == null) {
			return new ResponseEntity <>(HttpStatus.BAD_REQUEST);
		}
			return new ResponseEntity<>(actual, HttpStatus.OK);
	}
}
	
	
	
	
	
	