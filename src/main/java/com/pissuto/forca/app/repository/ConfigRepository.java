package com.pissuto.forca.app.repository;

import com.pissuto.forca.domain.ConfigJogosDomain.ConfigJogosDomain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfigRepository extends MongoRepository<ConfigJogosDomain, String> {

    List<ConfigJogosDomain> findAll();

    Optional<ConfigJogosDomain> findById (String id);

    //ConfigJogosDomain findByJogo(String levels);

    void deleteById (String id);

    ConfigJogosDomain save (ConfigJogosDomain jogo);
}
