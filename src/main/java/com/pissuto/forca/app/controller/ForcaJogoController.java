package com.pissuto.forca.app.controller;

import com.pissuto.forca.app.dto.ForcaJogadorResponseDto;
import com.pissuto.forca.app.dto.ForcaJogoResponseDto;
import com.pissuto.forca.app.services.ServiceForcaJogo;
import com.pissuto.forca.app.to.PalpiteTo;
import com.pissuto.forca.domain.ForcaJogadorDomain;
import com.pissuto.forca.domain.ForcaJogoDomain;
import com.pissuto.forca.infra.exceptions.BussinesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jogo")
@CrossOrigin("*")
public class ForcaJogoController {

    @Autowired
    private ServiceForcaJogo serviceForcaJogo;

    public String dificuldade = "medium";

    @GetMapping("/iniciar")
    public ResponseEntity<ForcaJogoResponseDto> iniciarJogo(@RequestParam(name = "email", required = false) String emailUsuario) throws BussinesException {
        ForcaJogoResponseDto dto = serviceForcaJogo.iniciarNovoJogo(dificuldade, emailUsuario);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{id}/palpite")
    private ResponseEntity<ForcaJogoResponseDto> validarPalpite(@PathVariable int id, @RequestBody PalpiteTo palpite) throws BussinesException {
        return ResponseEntity.ok(serviceForcaJogo.validarPalpite(id, palpite));
    }

    @PostMapping("/{id}/adivinhar")
    private ResponseEntity<ForcaJogoResponseDto> tentarAdivinharPalavra(@PathVariable int id, @RequestBody PalpiteTo tentativa) throws BussinesException {
        return ResponseEntity.ok(serviceForcaJogo.tentarAdivinharPalavra(id, tentativa));
    }

    @GetMapping("/{id}/dica")
    private ResponseEntity<ForcaJogoResponseDto> fornecerDica(@PathVariable int id, @RequestParam(name = "email", required = false) String email) throws BussinesException {
        return ResponseEntity.ok(serviceForcaJogo.fornecerDica(id, email));
    }

    @GetMapping("/UsuariosAnonimos")
    private ResponseEntity<ForcaJogadorDomain> visualizarUsuariosAnonimos(){
        return ResponseEntity.ok(serviceForcaJogo.visualizarAnonimo());
    }

    @GetMapping("/{id}/{email}")
    private ResponseEntity<ForcaJogoResponseDto> getJogoById(@PathVariable int id, @PathVariable String email) throws BussinesException {
        return ResponseEntity.ok(serviceForcaJogo.bucasJogoById(id, email));
    }

    @GetMapping("/{email}")
    private ResponseEntity<List<ForcaJogoDomain>> getJogos(@PathVariable String email) throws BussinesException {
        return ResponseEntity.ok(serviceForcaJogo.buscarJogosJogador(email));
    }
}
