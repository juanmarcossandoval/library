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
@Table (name = "editoriales")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Editorial {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_editorial;
	
	@Column (name = "nombre", nullable = false, unique = true, length = 150 )
	private String nombre;
}
