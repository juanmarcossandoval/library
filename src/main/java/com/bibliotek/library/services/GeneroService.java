package com.bibliotek.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bibliotek.library.entities.Genero;
import com.bibliotek.library.repositories.GeneroRepository;


@Service
public class GeneroService {

	private GeneroRepository generoRepository;

	public GeneroService(GeneroRepository generoRepository) {
		this.generoRepository = generoRepository;
	}
	
	public Genero guardar(Genero genero) {
		return generoRepository.save(genero);
	}
	
	public Genero buscarPorId(Long id){
		Optional<Genero> encontrado = generoRepository.findById(id);
		return encontrado.orElse(null);
	}
	
	public void eliminar(Long id) {
		generoRepository.deleteById(id);
	}
	
	public List<Genero> listarTodos(){
		return generoRepository.findAll();
	}
}