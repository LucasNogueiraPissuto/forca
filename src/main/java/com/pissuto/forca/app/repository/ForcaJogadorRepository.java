package com.pissuto.forca.app.repository;

import com.pissuto.forca.domain.ForcaJogadorDomain;
import com.pissuto.forca.domain.ForcaJogoDomain;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ForcaJogadorRepository extends MongoRepository<ForcaJogadorDomain, String> {

    List<ForcaJogadorDomain> findAll();

    Optional<ForcaJogadorDomain> findById (String id);

    void deleteById (String id);

    Optional<ForcaJogadorDomain> findByEmail(String email);

    Optional<ForcaJogadorDomain> findFirstByEmailIsNull();

    ForcaJogadorDomain save (ForcaJogadorDomain forcaJogadorDomain);

}
