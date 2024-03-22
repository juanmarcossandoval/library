package com.bibliotek.library.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bibliotek.library.exceptions.BadRequestException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.bibliotek.library.entities.Provincia;
import com.bibliotek.library.exceptions.NotFoundException;
import com.bibliotek.library.services.ProvinciaService;

class ProvinciaControllerTest {
	
	ProvinciaService mockProvServ;
	ProvinciaController provinciaController;
	Provincia prov1, prov2;
	List<Provincia> provincias;
	List<Provincia> filtradas;

	/*
		Estructura de los test
		when (cuando llames a la dependencia MOCKEADA hace TAL COSA)
		then (entonces ) puede fallar o salir bien
		assert (compara los resultados para saber si el TEST salio BIEN o No)
	 */

	@BeforeAll // se ejecuta antes de todos los test
    static void testbeforeAll(){
		System.out.println("Comenzando los test");
	}

	@BeforeEach // se ejecuta antes de cada test
	void setUp() {
		System.out.println("Ejecutando setUp...");
		mockProvServ = Mockito.mock(ProvinciaService.class);
		provinciaController = new ProvinciaController(mockProvServ);
		prov1 = new Provincia(1L,"Buenos Aires");
		prov2 = new Provincia(2L,"Chubut");
		provincias = new ArrayList<>();
		filtradas = new ArrayList<>();
		provincias.add(prov1);
		provincias.add(prov2);
		filtradas.add(prov1);
	}

	@Test // indica que es un test
	void testFindOne() throws NotFoundException {
		when(mockProvServ.buscarPorId(1L)).thenReturn(prov1);
		ResponseEntity<?> esperado = new ResponseEntity<>(prov1,HttpStatus.OK);
		ResponseEntity<?> testeado = provinciaController.findOne(1L);
		assertEquals(testeado,esperado);
	}
	
	@Test
	void testFindOneNotFound() throws NotFoundException{
		when(mockProvServ.buscarPorId(3L))
			.thenThrow(new NotFoundException("NOT FOUND"));
		assertThrowsExactly(
				NotFoundException.class,
				()-> provinciaController.findOne(3L));
	}

	@Test
	void testPostOne() throws BadRequestException {
		when(mockProvServ.crearNuevaP(prov1)).thenReturn(prov1);
		ResponseEntity<?> esperado = new ResponseEntity<>(prov1,HttpStatus.CREATED);
		ResponseEntity<?> testado = provinciaController.postOne(prov1);
		assertEquals(esperado,testado);
		verify(mockProvServ, times(1)).crearNuevaP(prov1);
	}

	@Test
	void testPostOneBadRequest() throws BadRequestException {
		when(mockProvServ.crearNuevaP(prov1)).thenThrow(new BadRequestException("bad request"));
		assertThrowsExactly(
				BadRequestException.class,
				() -> provinciaController.postOne(prov1)
		);
	}

	@Test
	void testFindByNameAll() {
		when(mockProvServ.filtrar(Optional.of(""))).thenReturn(provincias);
		ResponseEntity<?> esperado = new ResponseEntity<>(provincias,HttpStatus.OK);
		ResponseEntity<?> testeado = provinciaController.findByName(Optional.of(""));
		assertEquals(esperado, testeado);
	}
	
	@Test
	void testFindByName() {
		when(mockProvServ.filtrar(Optional.of("Buenos Aires"))).thenReturn(filtradas);
		ResponseEntity<?> esperado = new ResponseEntity<>(filtradas,HttpStatus.OK);
		ResponseEntity<?> testeado = provinciaController.findByName(Optional.of("Buenos Aires"));
		assertEquals(esperado, testeado);
	}
	
	@Test
	void testPutOne() throws BadRequestException, NotFoundException {
		when(mockProvServ.actualizar(prov1)).thenReturn(prov1);
		ResponseEntity<?> esperado = new ResponseEntity<>(prov1,HttpStatus.OK);
		ResponseEntity<?> testeado = provinciaController.putOne(prov1);
		assertEquals(esperado,testeado);
	}
	
	@Test
	void tesputOneBadRequest() throws BadRequestException, NotFoundException {
		when(mockProvServ.actualizar(prov1)).thenThrow(
				new BadRequestException("Fallo")
				);
		assertThrowsExactly(BadRequestException.class,
				()->provinciaController.putOne(prov1));       	
	}
	
	@Test
	void tesputOneNotFound() throws BadRequestException, NotFoundException {
		when(mockProvServ.actualizar(prov1)).thenThrow(
				new NotFoundException("Fallo")
				);
		assertThrowsExactly(NotFoundException.class,
				()->provinciaController.putOne(prov1));       	
	}
	
	@Test
	void testDeleteOne() {
		provinciaController.deleteOne(1L);
		verify(mockProvServ, times(1)).eliminarPorid(1L);
	}
		
	@Test
	void testPostOneNotFound () throws NotFoundException, BadRequestException {
		when(mockProvServ.crearNuevaP(prov1)).thenThrow(new NotFoundException("Not Found"));
		assertThrowsExactly(
				NotFoundException.class,
				()-> provinciaController.postOne(prov1));
	}
	@AfterAll // se ejecuta luego de todos los test
    static void afterAll(){
		System.out.println("terminando los test");
	}
}
