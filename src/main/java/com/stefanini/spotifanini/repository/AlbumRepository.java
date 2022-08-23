package com.stefanini.spotifanini.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stefanini.spotifanini.model.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    Optional<Album> findByName(String name);
}
