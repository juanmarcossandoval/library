package com.bibliotek.library.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="estados prestamos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoPrestamo {
	
	@Id
	private Long id_estado;
	
	@Column (name = "estado", nullable = false, unique = true, length = 50)
	private String estado;
}
