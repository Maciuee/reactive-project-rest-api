package com.project.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.project.model.Zadanie;

@Repository
public interface ZadanieRepository extends ReactiveCrudRepository<Zadanie, Integer> {

}
