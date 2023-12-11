package com.anime.service;

import com.anime.dtos.request.AnimeRequest;
import com.anime.dtos.response.AnimeResponse;
import com.anime.model.AnimeModel;
import com.anime.repository.AnimeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimeService {
    private AnimeRepository animeRepository;
    private ModelMapper modelMapper;

    public AnimeService(AnimeRepository animeRepository, ModelMapper modelMapper) {
        this.animeRepository = animeRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<AnimeResponse> criarAnime(AnimeRequest animeRequest) {
        AnimeModel animeModel = modelMapper.map(animeRequest, AnimeModel.class);
        AnimeModel salvoModel = animeRepository.save(animeModel);
        AnimeResponse animeResponse = modelMapper.map(salvoModel, AnimeResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(animeResponse);
    }

    public ResponseEntity<List> listarTodos() {
        List<AnimeModel> all = animeRepository.findAll();
        List<AnimeResponse> collect1 = all.stream().map(animeModel -> modelMapper.map(animeModel, AnimeResponse.class)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(collect1);
    }
}
