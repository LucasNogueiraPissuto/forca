package com.pissuto.forca.app.repository;

import com.pissuto.forca.domain.ConfigJogosDomain;
import com.pissuto.forca.domain.WordDomain;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends MongoRepository<WordDomain, String> {
    List<WordDomain> findAll();

    Optional<WordDomain> findById (String id);

    //ConfigJogosDomain findByJogo(String levels);

    void deleteById (String id);

    WordDomain save (WordDomain palavra);
}
