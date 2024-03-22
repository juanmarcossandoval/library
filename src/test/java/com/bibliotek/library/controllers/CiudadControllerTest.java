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

import com.bibliotek.library.entities.Ciudad;
import com.bibliotek.library.exceptions.BadRequestException;
import com.bibliotek.library.exceptions.NotFoundException;
import com.bibliotek.library.services.CiudadService;

class CiudadControllerTest {
	
	CiudadService mockCiudadServ;
	CiudadController ciudadController;
	Ciudad city1, city2;
	List<Ciudad> ciudades;
	List<Ciudad> filtradas;

	@BeforeAll // se ejecuta antes de todos los test
    static void testbeforeAll(){
		System.out.println("Comenzando los test");
	}
	
	@BeforeEach
	void setUp() {
		System.out.println("Ejecutando SetUp...");
		mockCiudadServ = Mockito.mock(CiudadService.class);
		ciudadController = new CiudadController(mockCiudadServ);
		city1 = new Ciudad(1L,"La Plata", null);
		city2 = new Ciudad(2L,"Ensenada", null);
		ciudades = new ArrayList<>();
		filtradas = new ArrayList<>();
		ciudades.add(city1);
		ciudades.add(city2);
		filtradas.add(city1);
	}
	
	@Test
	void testPostOne() throws BadRequestException, NotFoundException {
		when(mockCiudadServ.crearNuevaC(city1)).thenReturn(city1);
		ResponseEntity<?> esperado = new ResponseEntity<>(city1,HttpStatus.CREATED);
		ResponseEntity<?> testeado = ciudadController.postOne(city1);
		assertEquals(esperado, testeado);
		verify(mockCiudadServ, times(1)).crearNuevaC(city1);
		}

	@Test
	void testPostOneBadRequest() throws BadRequestException, NotFoundException {
		when(mockCiudadServ.crearNuevaC(city1)).thenThrow(new BadRequestException("Bad Request"));
		assertThrowsExactly(
				BadRequestException.class,
				() -> ciudadController.postOne(city1));
	}
	
	@Test
	void testPostOneNotFound() throws BadRequestException, NotFoundException {
		when(mockCiudadServ.crearNuevaC(city1)).thenThrow(new NotFoundException("Not Found"));
		assertThrowsExactly(
				NotFoundException.class,
				() -> ciudadController.postOne(city1));
	}
	@Test
	void testFindOne() throws NotFoundException {
		when(mockCiudadServ.buscarPorId(1L)).thenReturn(city1);
		ResponseEntity<?> esperado = new ResponseEntity<>(city1, HttpStatus.OK);
		ResponseEntity<?> testeado = ciudadController.findOne(1L);
		assertEquals(esperado, testeado);
	}
	
	@Test
	void testFindOneNotFound() throws NotFoundException {
		when(mockCiudadServ.buscarPorId(3L))
			.thenThrow(new NotFoundException("NOT FOUND"));
		assertThrowsExactly(
				NotFoundException.class,
				() -> ciudadController.findOne(3L));
	}
	
	@Test
	void testFindByNameAll() {
		when(mockCiudadServ.filtrar(Optional.of(""))).thenReturn(ciudades);
		ResponseEntity<?> esperado = new ResponseEntity<>(ciudades,HttpStatus.OK);
		ResponseEntity<?> testeado = ciudadController.getAll(Optional.of(""));
		assertEquals(esperado, testeado);
	}
	
	@Test
	void testFindByName() {
		when(mockCiudadServ.filtrar(Optional.of("La Plata"))).thenReturn(filtradas);
		ResponseEntity<?> esperado = new ResponseEntity<>(filtradas,HttpStatus.OK);
		ResponseEntity<?> testeado = ciudadController.getAll(Optional.of("La Plata"));
		assertEquals(esperado, testeado);
	}
	
	@Test
	void TestPutOne() throws NotFoundException, BadRequestException {
		when(mockCiudadServ.actualizar(city1)).thenReturn(city1);
		ResponseEntity<?> esperado = new ResponseEntity<>(city1,HttpStatus.OK);
		ResponseEntity<?> testeado = ciudadController.putOne(city1);
		assertEquals(esperado, testeado); 
	}
	
	@Test
	void testPutOneBadReques() throws NotFoundException, BadRequestException {
		when(mockCiudadServ.actualizar(city1))
			.thenThrow(new BadRequestException("Bad Request"));
		assertThrowsExactly(
				BadRequestException.class,
				() -> ciudadController.putOne(city1));
	}
	
	@Test
	void testPutOneNotFound() throws NotFoundException, BadRequestException {
		when(mockCiudadServ.actualizar(city1))
			.thenThrow(new NotFoundException("NOT FOUND"));
		assertThrowsExactly(
				NotFoundException.class,
				() -> ciudadController.putOne(city1));
	}
	
	@Test
	void testDeleteOne() {
		ciudadController.deleteOne(1L);
		verify(mockCiudadServ, times(1)).eliminarPorId(1L);
	}
	
	@AfterAll // se ejecuta luego de todos los test
    static void afterAll(){
		System.out.println("terminando los test");
	}
}
