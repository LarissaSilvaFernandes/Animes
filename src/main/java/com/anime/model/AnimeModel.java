package com.anime.model;

import com.anime.model.enums.AvaliacaoAnime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class AnimeModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String titulo;
    private String genero;
    private LocalDate anoDeLancamento;
    private String sinopse;
    private AvaliacaoAnime avaliacao;
}
