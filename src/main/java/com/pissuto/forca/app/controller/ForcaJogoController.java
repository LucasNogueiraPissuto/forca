package com.pissuto.forca.app.controller;

import com.pissuto.forca.app.dto.ForcaJogadorResponseDto;
import com.pissuto.forca.app.dto.ForcaJogoResponseDto;
import com.pissuto.forca.app.services.ServiceForcaJogo;
import com.pissuto.forca.app.to.PalpiteTo;
import com.pissuto.forca.infra.exceptions.BussinesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jogo")
public class ForcaJogoController {

    @Autowired
    private ServiceForcaJogo serviceForcaJogo;

    public String dificuldade = "medium";

    @GetMapping("/iniciar/{email}")
    private ResponseEntity<ForcaJogoResponseDto> inciarJogo(@PathVariable String email) throws BussinesException {
        return ResponseEntity.ok(serviceForcaJogo.iniciarNovoJogo(dificuldade, email));
    }

    @PostMapping("/{id}/palpite")
    private ResponseEntity<ForcaJogoResponseDto> validarPalpite(@PathVariable int id, @RequestBody PalpiteTo palpite) throws BussinesException {
        return ResponseEntity.ok(serviceForcaJogo.validarPalpite(id, palpite));
    }
}
