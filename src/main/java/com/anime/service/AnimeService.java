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
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnimeService {
    private final AnimeRepository animeRepository;
    private final ModelMapper modelMapper;

    public AnimeService(AnimeRepository animeRepository, ModelMapper modelMapper) {
        this.animeRepository = animeRepository;
        this.modelMapper = modelMapper;
    }

    public AnimeResponse criarAnime(AnimeRequest animeRequest) {
        AnimeModel animeModel = modelMapper.map(animeRequest, AnimeModel.class);
        AnimeModel salvoModel = animeRepository.save(animeModel);
        return modelMapper.map(salvoModel, AnimeResponse.class);
    }

    public Page<AnimeResponse> listarOuFiltragem(String genero, Pageable pageable) {
        Page<AnimeModel> results;

        if (ObjectUtils.isEmpty(genero)) {
            results = animeRepository.findAllByOrderByGeneroAsc(pageable);
        } else {
            results = animeRepository.findAllByGeneroContainingOrderByGeneroAsc(genero, pageable);
        }

        if (ObjectUtils.isEmpty(results.getContent())) {
            return Page.empty(pageable);
        }

        List<AnimeResponse> collect = results.getContent().stream()
                .map(animeModel -> modelMapper.map(animeModel, AnimeResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(collect, pageable, results.getTotalElements());
    }

    public AnimeResponse buscarPorId(UUID id) {
        Optional<AnimeModel> byId = animeRepository.findById(id);
        if (byId.isEmpty()) {
            throw new NaoEncontradoException(id);
        }
        return modelMapper.map(byId, AnimeResponse.class);
    }

    public AnimeResponse atualizarAnime(UUID id, AnimeRequest animeRequest) {
        Optional<AnimeModel> byId = animeRepository.findById(id);
        if (byId.isEmpty()) {
            throw new NaoEncontradoException(id);
        }
        if (!ObjectUtils.isEmpty(animeRequest.getTitulo())) {
            byId.get().setTitulo(animeRequest.getTitulo());
        }
        if (!ObjectUtils.isEmpty(animeRequest.getGenero())) {
            byId.get().setGenero(animeRequest.getGenero());
        }
        if (!ObjectUtils.isEmpty(animeRequest.getAnoDeLancamento())) {
            byId.get().setAnoDeLancamento(animeRequest.getAnoDeLancamento());
        }
        if (!ObjectUtils.isEmpty(animeRequest.getSinopse())) {
            byId.get().setSinopse(animeRequest.getSinopse());
        }
        if (!ObjectUtils.isEmpty(animeRequest.getAvaliacao())) {
            byId.get().setAvaliacao(animeRequest.getAvaliacao());
        }
        AnimeModel animeModel = animeRepository.save(byId.get());
        return modelMapper.map(animeModel, AnimeResponse.class);
    }

    public void deletarAnime(UUID id) {
        Optional<AnimeModel> byId = animeRepository.findById(id);
        if (byId.isEmpty()) {
            throw new NaoEncontradoException(id);
        }
        animeRepository.deleteById(id);
    }

}
