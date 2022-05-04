package com.project.model;

import java.time.LocalDate;
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
@Table("projekt")
public class Projekt {
	@Id
	@Column("projekt_id")
	private Integer projektId;

	private String nazwa;

	private String opis;

	@Column("dataczas_utworzenia")
	private LocalDateTime dataczasUtworzenia;

	@Column("dataczas_modyfikacji")
	private LocalDateTime dataczasModyfikacji;

	@Column("data_oddania")
	private LocalDate dataOddania;

	public Projekt(String nazwa, String opis, LocalDateTime dataczasUtworzenia, LocalDate dataOddania) {
		this.nazwa = nazwa;
		this.opis = opis;
		this.dataczasUtworzenia = dataczasUtworzenia;
		this.dataOddania = dataOddania;
	}
}
