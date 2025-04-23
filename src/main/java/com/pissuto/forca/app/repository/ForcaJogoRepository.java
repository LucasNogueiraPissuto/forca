package com.pissuto.forca.app.repository;

import com.pissuto.forca.domain.ConfigJogosDomain.ConfigJogosDomain;
import com.pissuto.forca.domain.ForcaJogoDomain;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ForcaJogoRepository extends MongoRepository<ForcaJogoDomain, String> {

    List<ForcaJogoDomain> findAll();

    Optional<ForcaJogoDomain> findById (String id);

    void deleteById (String id);

    ForcaJogoDomain save (ForcaJogoDomain forcaJogoDomain);

}
