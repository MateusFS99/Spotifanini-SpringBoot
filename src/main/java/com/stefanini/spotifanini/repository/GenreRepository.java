package com.stefanini.spotifanini.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stefanini.spotifanini.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByName(String name);
}
