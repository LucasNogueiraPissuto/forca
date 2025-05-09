package com.pissuto.forca.app.controller;

import com.pissuto.forca.app.dto.WordDto;
import com.pissuto.forca.app.services.ServiceWord;
import com.pissuto.forca.app.to.WordTo;
import com.pissuto.forca.domain.WordDomain;
import com.pissuto.forca.infra.exceptions.BussinesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forca/palavra")
public class WordController {

    @Autowired
    private ServiceWord serviceWord;

    @PostMapping
    public ResponseEntity<WordDto> cadastroPalavra(@RequestBody WordTo body) throws BussinesException {
        return ResponseEntity.ok(serviceWord.saveNewWord(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WordDto> atualizarPalavra(@RequestBody WordTo body, @PathVariable String id){
        return ResponseEntity.ok(serviceWord.atualizarPalavra(body, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WordDto> deletarPalavra(@PathVariable String id) throws BussinesException {
        return ResponseEntity.ok(serviceWord.deleteWord(id));
    }
}
