package com.stefanini.spotifanini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stefanini.spotifanini.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long>{
    
    List<Compilation> findByName(String name);
}
