package com.pissuto.forca.app.services;

import com.pissuto.forca.app.dto.WordDto;
import com.pissuto.forca.app.repository.WordRepository;
import com.pissuto.forca.app.to.WordTo;
import com.pissuto.forca.domain.WordDomain;
import com.pissuto.forca.infra.exceptions.BussinesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceWord {

    @Autowired
    private WordRepository wordRepository;

    public WordDto saveNewWord(WordTo body) throws BussinesException {
        var saved = new WordDomain();
        saved.setPalavra(body.getPalavra());
        saved.setDicas(body.getDicas());

        saved = wordRepository.save(saved);

        if (saved.getId() != null && !saved.getId().isBlank()) {
            return parseWordDto(saved);
        } else {
            throw new BussinesException("Não foi possível salvar a palavra");
        }
    }

    private WordDto parseWordDto(WordDomain saved) {
        return new WordDto(
                saved.getId(),
                saved.getPalavra(),
                saved.getDicas()
        );
    }

    public List<WordDto> findAllWords() {
        return wordRepository.findAll().stream()
                .map(this::parseWordDto)
                .collect(Collectors.toList());
    }

    public void deleteWord(String id) throws BussinesException {
        if (wordRepository.existsById(id)) {
            wordRepository.deleteById(id);
        } else {
            throw new BussinesException("Palavra não encontrada para exclusão");
        }
    }

    public WordDto atualizarPalavra(WordTo body, String id) {
        Optional<WordDomain> palavraExistente = wordRepository.findById(id);
        WordDomain palavraExistenteDomain;

        if(palavraExistente.isPresent()){
            palavraExistenteDomain = palavraExistente.get();
            palavraExistenteDomain.atualizar(body.getPalavra(), body.getDicas());
            wordRepository.save(palavraExistenteDomain);
        }
        else {
            throw new RuntimeException("Palavra não encontrada");
        }

        return parseWordDto(palavraExistenteDomain);
    }
}
