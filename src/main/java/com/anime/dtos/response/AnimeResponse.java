package com.anime.dtos.response;

import com.anime.model.enums.AvaliacaoAnime;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class AnimeResponse {
    private UUID id;
    private String titulo;
    private String genero;
    private LocalDate anoDeLancamento;
    private String sinopse;
    private AvaliacaoAnime avaliacao;
}
