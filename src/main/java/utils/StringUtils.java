package utils;

public class StringUtils {
	/**
	 * retorna vacio si el string del parametro esta nulo vacio o en blanco
	 * @param s String a evaluar
	 * @return verdadero si esta vacio, blanco o nulo.
	 */
	public static boolean Check (String s) {
		return s.equals(null) || s.isBlank() || s.isEmpty();
	}
}
//los strings no se comparan por == (chequea la direciones de memoria, operador de igualdad)