package com.bibliotek.library.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "Domicilios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Domicilio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_domicilio;
	
	@Column (name = "direccion", nullable = false, unique = false, length = 100)
	private String direccion;
	
	@ManyToOne(optional = false)
	@JoinColumn(referencedColumnName = "id_ciudad")
	private Ciudad ciudad;
}
