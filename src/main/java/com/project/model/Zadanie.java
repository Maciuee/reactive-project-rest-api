package com.project.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("zadanie")
public class Zadanie {
	@Id
	@Column("zadanie_id")
	private Integer zadanieId;

	private String nazwa;

	private String opis;

	private Integer kolejnosc;

	@Column("dataczas_utworzenia")
	private LocalDateTime dataczasUtworzenia;
	
	@Column("dataczas_modyfikacji")
	private LocalDateTime dataczasModyfikacji;

	@Column("projekt_id")
	private Integer projektId;

}
