package com.stefanini.spotifanini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stefanini.spotifanini.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

}
