package com.anime.repository;

import com.anime.model.AnimeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnimeRepository extends JpaRepository<AnimeModel, UUID> {

    Page<AnimeModel> findAllByOrderByGeneroAsc(Pageable pageable);
    Page<AnimeModel> findAllByGeneroContainingOrderByGeneroAsc(String genero, Pageable pageable);


}
