package com.bibliotek.library.entities;


import jakarta.persistence.CascadeType;
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
@Table (name = "Ciudades")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ciudad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_ciudad;
	
	@Column (name = "nombre" , nullable = false, unique = true, length = 50)
	private String nombre;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_id_provincia" , referencedColumnName = "id_provincia")
	private Provincia provincia;
	
	
	
}