package com.pissuto.forca.app.controller;

import com.pissuto.forca.app.dto.ConfigJogosDto.ConfigJogosDto;
import com.pissuto.forca.app.services.ServiceJogosConfig;
import com.pissuto.forca.app.to.ConfigJogosTo.ConfigJogosTo;
import com.pissuto.forca.infra.exceptions.BussinesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forca/configure")
public class ConfigureController {

    @Autowired
    private ServiceJogosConfig serviceJogosConfig;

    @GetMapping("/{id}")
    public ConfigJogosDto CheckConfigure(@PathVariable String id) throws BussinesException {
       return serviceJogosConfig.retornaConfiguracao(id);
    }

    @PostMapping
    public ResponseEntity<ConfigJogosDto> createNewGameConfig(@RequestBody ConfigJogosTo body) throws BussinesException {
        return ResponseEntity.ok(serviceJogosConfig.saveNewConfig(body));
    }
}
