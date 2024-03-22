package com.bibliotek.library.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bibliotek.library.entities.Domicilio;
import com.bibliotek.library.exceptions.BadRequestException;
import com.bibliotek.library.exceptions.NotFoundException;
import com.bibliotek.library.services.DomicilioService;

class DomicilioControllerTestTest {
	
	DomicilioService mockDomiServ;
	DomicilioController domiController;
	Domicilio domi1, domi2;
	List<Domicilio> domicilios;
	List<Domicilio> filtradas;
	
	@BeforeAll // se ejecuta antes de todos los test
    static void testbeforeAll(){
		System.out.println("Comenzando los test");
	}
	
	@BeforeEach
	void setUp() {
		System.out.println("Ejecutando SetUp...");
		mockDomiServ = Mockito.mock(DomicilioService.class);
		domiController = new DomicilioController(mockDomiServ);
		domi1 = new Domicilio(1L,"Calle 74 N°444", null);
		domi2 = new Domicilio(2L,"Siempre viva 123", null);
		domicilios = new ArrayList<>();
		filtradas = new ArrayList<>();
		domicilios.add(domi1);
		domicilios.add(domi2);
		filtradas.add(domi1);
	}
	
	@Test
	void testPostOne() throws BadRequestException, NotFoundException {
		when(mockDomiServ.crearNuevod(domi1)).thenReturn(domi1);
		ResponseEntity<?> esperado = new ResponseEntity<>(domi1,HttpStatus.CREATED);
		ResponseEntity<?> testeado = domiController.postOne(domi1);
		assertEquals(esperado, testeado);
		verify(mockDomiServ, times(1)).crearNuevod(domi1);
	}
	
	@Test
	void testPostOneBadRequest() throws BadRequestException, NotFoundException {
		when(mockDomiServ.crearNuevod(domi1)).thenThrow(new BadRequestException("Bad Request"));
		assertThrowsExactly(
				BadRequestException.class,
				() -> domiController.postOne(domi1));
	}
	
	@Test
	void testPostOneNotFound() throws BadRequestException, NotFoundException {
		when(mockDomiServ.crearNuevod(domi1)).thenThrow(new NotFoundException("Not Found"));
		assertThrowsExactly(
				NotFoundException.class,
				() -> domiController.postOne(domi1));
	}
	
	@Test
	void testFindOne() throws NotFoundException {
		when(mockDomiServ.buscarPorId(1L)).thenReturn(domi1);
		ResponseEntity<?> esperado = new ResponseEntity<>(domi1, HttpStatus.OK);
		ResponseEntity<?> testeado = domiController.findOne(1L);
		assertEquals(esperado, testeado);
	}
	
	@Test
	void testFindOneNotFound() throws NotFoundException {
		when(mockDomiServ.buscarPorId(3L))
			.thenThrow(new NotFoundException("NOT FOUND"));
		assertThrowsExactly(
				NotFoundException.class,
				() -> domiController.findOne(3L));
	}
	
	@Test
	void testFindByNameAll() {
		when(mockDomiServ.filtrar(Optional.of(""))).thenReturn(domicilios);
		ResponseEntity<?> esperado = new ResponseEntity<>(domicilios,HttpStatus.OK);
		ResponseEntity<?> testeado = domiController.getAll(Optional.of(""));
		assertEquals(esperado, testeado);
	}
	
	@Test
	void testFindByName() {
		when(mockDomiServ.filtrar(Optional.of("La Plata"))).thenReturn(filtradas);
		ResponseEntity<?> esperado = new ResponseEntity<>(filtradas,HttpStatus.OK);
		ResponseEntity<?> testeado = domiController.getAll(Optional.of("La Plata"));
		assertEquals(esperado, testeado);
	}
	
	@Test
	void TestPutOne() throws NotFoundException, BadRequestException {
		when(mockDomiServ.actualizar(domi1)).thenReturn(domi1);
		ResponseEntity<?> esperado = new ResponseEntity<>(domi1,HttpStatus.OK);
		ResponseEntity<?> testeado = domiController.putOne(domi1);
		assertEquals(esperado, testeado); 
	}
	
	@Test
	void testPutOneBadRequest() throws NotFoundException, BadRequestException {
		when(mockDomiServ.actualizar(domi1))
			.thenThrow(new BadRequestException("Bad Request"));
		assertThrowsExactly(
				BadRequestException.class,
				() -> domiController.putOne(domi1));
	}

	@Test
	void testPutOneNotFound() throws NotFoundException, BadRequestException {
		when(mockDomiServ.actualizar(domi1))
			.thenThrow(new NotFoundException("NOT FOUND"));
		assertThrowsExactly(
				NotFoundException.class,
				() -> domiController.putOne(domi1));
	}
	
	@Test
	void testDeleteOne() {
		domiController.deleteOne(1L);
		verify(mockDomiServ, times(1)).eliminarPorId(1L);
	}
	
	@AfterAll // se ejecuta luego de todos los test
    static void afterAll(){
		System.out.println("terminando los test");
	}
}
