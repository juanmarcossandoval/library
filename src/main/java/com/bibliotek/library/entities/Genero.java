package com.bibliotek.library.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "generos_literarios")
@Data 
@AllArgsConstructor
@NoArgsConstructor

public class Genero {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_genero;
	
	@Column(name = "nombre", nullable = false, unique = true, length = 50)
	private String name;
	
	
}
