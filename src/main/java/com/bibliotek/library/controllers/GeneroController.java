package com.bibliotek.library.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bibliotek.library.entities.Genero;
import com.bibliotek.library.services.GeneroService;

@Controller
@RequestMapping("/GeneroLiterario")
public class GeneroController {

	private GeneroService generoService;

	public GeneroController(GeneroService generoService) {
		this.generoService = generoService;
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		List<Genero> lista = generoService.listarTodos();
		return new ResponseEntity<>(lista,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> postOne(@RequestBody Genero genero){
		Genero nuevo = generoService.guardar(genero);
		return new ResponseEntity<> (nuevo,HttpStatus.CREATED);
	}
	
	
}
