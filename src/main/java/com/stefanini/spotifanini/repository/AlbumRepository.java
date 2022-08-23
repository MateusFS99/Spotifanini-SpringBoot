package com.stefanini.spotifanini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stefanini.spotifanini.model.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findByName(String name);
}
