package com.anime.dtos.request;

import com.anime.model.enums.AvaliacaoAnime;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AnimeRequest {
    @NotBlank
    private String titulo;
    @NotBlank
    private String genero;
    @NotBlank
    private String sinopse;
    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate anoDeLancamento;
    private AvaliacaoAnime avaliacao;
}
