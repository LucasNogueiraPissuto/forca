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
            return parseWordDto(saved, "Palavra cadastrada com sucesso!");
        } else {
            throw new BussinesException("Não foi possível salvar a palavra");
        }
    }

    private WordDto parseWordDto(WordDomain saved, String mensagem) {
        return new WordDto(
                saved.getId(),
                saved.getPalavra(),
                saved.getDicas(),
                mensagem
        );
    }

    public WordDto deleteWord(String id) throws BussinesException {
        Optional<WordDomain> palavraOptional = wordRepository.findById(id);

        if (palavraOptional.isPresent()) {
            WordDomain palavra = palavraOptional.get();
            wordRepository.deleteById(id);

            return new WordDto(
                    palavra.getId(),
                    palavra.getPalavra(),
                    palavra.getDicas(),
                    "Palavra deletada com sucesso");
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

        return parseWordDto(palavraExistenteDomain, "Palavra atualizada com sucesso!");
    }

}
