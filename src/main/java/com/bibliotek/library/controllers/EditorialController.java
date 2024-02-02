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

import com.bibliotek.library.entities.Editorial;
import com.bibliotek.library.services.EditorialService;

@Controller
@RequestMapping("/Editoriales")

public class EditorialController {

	private EditorialService editorialService;
	
	public EditorialController(EditorialService editorialService) {
		this.editorialService = editorialService;
	}
	
	@PostMapping
	public ResponseEntity<?> postone(@RequestBody Editorial nuevo){
		Editorial nuevoe = editorialService.guardar(nuevo);
		if (nuevoe == null) {
			return new ResponseEntity<>("El nombre no puede estar vacio", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(nuevoe, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity <?> getAll(){
		List<Editorial> lista = editorialService.listarTodos();
		return new ResponseEntity<>(lista,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <?> finOne(@PathVariable Long id){
		Editorial encontrado = editorialService.buscarPorId(id);
		if(encontrado == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(encontrado,HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<?>putOne(@RequestBody Editorial nuevo){
		Editorial actualizado = editorialService.guardar(nuevo);
		return new ResponseEntity<>(actualizado,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable Long id){
		editorialService.eliminarPorId(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<?>patchOne(@RequestBody Editorial actualizado){
		Editorial actual = editorialService.actualizar(actualizado);
		if(actual == null) {
			return new ResponseEntity <>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(actual, HttpStatus.OK);
	}
}
