package com.anime.controller;

import com.anime.dtos.request.AnimeRequest;
import com.anime.dtos.response.AnimeResponse;
import com.anime.service.AnimeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rota-anime")
public class AnimeController {

    private AnimeService animeService;

    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @PostMapping
    public ResponseEntity<AnimeResponse> criarAnime(@RequestBody @Valid AnimeRequest animeRequest) {
        return animeService.criarAnime(animeRequest);
    }


    @GetMapping(("/busca"))
    public ResponseEntity<Page<AnimeResponse>> filtragemPorGeneroELancamento(@RequestParam(name = "genero", required = false) String genero,
                                                                             @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                                             @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return animeService.listarOuFiltragem(genero, pageable);
    }

    @GetMapping(("/{id}"))
    public ResponseEntity<AnimeResponse> buscarPorId(@PathVariable UUID id) {
        return animeService.buscarPorId(id);
    }
}
