package com.anime.service;

import com.anime.dtos.request.AnimeRequest;
import com.anime.dtos.response.AnimeResponse;
import com.anime.exception.NaoEncontradoException;
import com.anime.model.AnimeModel;
import com.anime.repository.AnimeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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


    public ResponseEntity<Page<AnimeResponse>> listarOuFiltragem(String genero, Pageable pageable) {
        Page<AnimeModel> results;

        if (ObjectUtils.isEmpty(genero)) {
            results = animeRepository.findAllByOrderByGeneroAsc(pageable);
        } else {
            results = animeRepository.findAllByGeneroContainingOrderByGeneroAsc(genero, pageable);
        }

        if (ObjectUtils.isEmpty(results.getContent())) {
            return ResponseEntity.status(HttpStatus.OK).body(Page.empty(pageable));
        }

        List<AnimeResponse> collect = results.getContent().stream()
                .map(animeModel -> modelMapper.map(animeModel, AnimeResponse.class))
                .collect(Collectors.toList());

        Page<AnimeResponse> responsePage = new PageImpl<>(collect, pageable, results.getTotalElements());

        return ResponseEntity.status(HttpStatus.OK).body(responsePage);
    }

    public ResponseEntity<AnimeResponse> buscarPorId(UUID id) {
        Optional<AnimeModel> byId = animeRepository.findById(id);
        if (byId.isEmpty()) {
            throw new NaoEncontradoException(id);
        }
        AnimeResponse animeResponse = modelMapper.map(byId, AnimeResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(animeResponse);
    }


}
