package com.stefanini.spotifanini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stefanini.spotifanini.model.Music;

public interface MusicRepository extends JpaRepository<Music, Long>{
    
    List<Music> findByName(String name);
}
