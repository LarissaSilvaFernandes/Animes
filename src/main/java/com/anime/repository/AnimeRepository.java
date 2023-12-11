package com.anime.repository;

import com.anime.model.AnimeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnimeRepository extends JpaRepository<AnimeModel, UUID> {

}
