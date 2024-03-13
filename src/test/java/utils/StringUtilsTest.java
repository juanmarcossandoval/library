package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

	private final String OK = "contenido";
	private final String VACIO = "";
	private final String BLANCO = "   ";
	private final String NULO = null;
	
	@Test
	void string_Ok() {
		assertFalse(StringUtils.Check(OK));
	}
	
	@Test 
	void string_Nulo() {
		assertTrue(StringUtils.Check(NULO));
	}
	

	@Test
	void string_Vacio() {
		assertTrue(StringUtils.Check(VACIO));
	}
	
	@Test
	void string_Blanco() {
		assertTrue(StringUtils.Check(BLANCO));
	}
}
