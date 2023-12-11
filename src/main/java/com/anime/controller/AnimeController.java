package com.anime.controller;

import com.anime.dtos.request.AnimeRequest;
import com.anime.dtos.response.AnimeResponse;
import com.anime.service.AnimeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List> listarTodos() {
        return animeService.listarTodos();
    }
}
