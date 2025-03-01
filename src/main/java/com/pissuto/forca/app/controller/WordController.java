package com.pissuto.forca.app.controller;

import com.pissuto.forca.app.dto.WordDto;
import com.pissuto.forca.app.services.ServiceWord;
import com.pissuto.forca.app.to.WordTo;
import com.pissuto.forca.infra.exceptions.BussinesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forca/palavra")
public class WordController {

    @Autowired
    private ServiceWord serviceWord;

    @PostMapping
    public ResponseEntity<WordDto> cadastroPalavra(@RequestBody WordTo body) throws BussinesException {
        return ResponseEntity.ok(serviceWord.saveNewWord(body));
    }
}
