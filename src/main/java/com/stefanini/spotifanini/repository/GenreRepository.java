package com.stefanini.spotifanini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stefanini.spotifanini.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long>{
    
}
