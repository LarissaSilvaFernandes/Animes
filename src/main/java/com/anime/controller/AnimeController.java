package com.anime.controller;

import com.anime.dtos.request.AnimeRequest;
import com.anime.dtos.response.AnimeResponse;
import com.anime.service.AnimeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/rota-anime")
public class AnimeController {

    private final AnimeService animeService;

    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @PostMapping
    public ResponseEntity<AnimeResponse> criarAnime(@RequestBody @Valid AnimeRequest animeRequest) {
        AnimeResponse animeResponse = animeService.criarAnime(animeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(animeResponse);
    }


    @GetMapping(("/busca"))
    public ResponseEntity<Page<AnimeResponse>> filtragemPorGeneroELancamento(@RequestParam(name = "genero", required = false) String genero,
                                                                             @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                                             @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<AnimeResponse> animeResponses = animeService.listarOuFiltragem(genero, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(animeResponses);
    }

    @GetMapping(("/{id}"))
    public ResponseEntity<AnimeResponse> buscarPorId(@PathVariable UUID id) {
        AnimeResponse animeResponse = animeService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(animeResponse);
    }

    @PutMapping(("/{id}"))
    public ResponseEntity<AnimeResponse> atualizarAnime(@PathVariable UUID id, @RequestBody AnimeRequest animeRequest) {
        AnimeResponse animeResponse = animeService.atualizarAnime(id, animeRequest);
        return ResponseEntity.status(HttpStatus.OK).body(animeResponse);
    }

    @DeleteMapping(("/{id}"))
    public ResponseEntity<Void> deletarAnime(@PathVariable UUID id) {
        animeService.deletarAnime(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
