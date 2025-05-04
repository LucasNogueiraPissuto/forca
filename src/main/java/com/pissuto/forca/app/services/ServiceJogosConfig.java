package com.pissuto.forca.app.services;

import com.pissuto.forca.app.dto.ConfigJogosDto.ConfigJogosDto;
import com.pissuto.forca.app.dto.ConfigJogosDto.ModoCaosDto;
import com.pissuto.forca.app.dto.ConfigJogosDto.NivelConfigDto;
import com.pissuto.forca.app.repository.ConfigRepository;
import com.pissuto.forca.app.to.ConfigJogosTo.ConfigJogosTo;
import com.pissuto.forca.domain.ConfigJogosDomain.ConfigJogosDomain;
import com.pissuto.forca.domain.ConfigJogosDomain.ModoCaosDomain;
import com.pissuto.forca.domain.ConfigJogosDomain.NivelConfigDomain;
import com.pissuto.forca.infra.exceptions.BussinesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceJogosConfig {

    @Autowired
    private ConfigRepository configRepository;

    public ConfigJogosDto saveNewConfig(ConfigJogosTo body) throws BussinesException {
        System.out.println("Body: " + body.getLevels());
        var saved = new ConfigJogosDomain();
        saved.setLevels(body.getLevels().stream().map(levelTo -> {
            var level = new NivelConfigDomain();
            level.setLevelName(levelTo.getLevelName());
            level.setDeathTime(levelTo.getDeathTime());
            level.setMoreSuggestions(levelTo.isMoreSuggestions());
            level.setMaxErrors(levelTo.getMaxErrors());
            level.setHintsAllowed(levelTo.isHintsAllowed());
            level.setTimer(levelTo.isTimer());

            if (levelTo.getChaosMode() != null) {
                var chaosMode = new ModoCaosDomain();
                chaosMode.setShuffleInterval(levelTo.getChaosMode().getShuffleInterval());
                chaosMode.setDisappearanceInterval(levelTo.getChaosMode().getDisappearanceInterval());
                chaosMode.setInitialDelay(levelTo.getChaosMode().getInitialDelay());
                chaosMode.setHardcoreMode(levelTo.getChaosMode().isHardcoreMode());
                chaosMode.setStrategicMode(levelTo.getChaosMode().isStrategicMode());
                level.setChaosMode(chaosMode);
            }
            return level;
        }).collect(Collectors.toList()));

        saved = configRepository.save(saved);

        if (saved.getId() != null && !saved.getId().isBlank()) {
            return parsedConfigJogoDto(saved);
        } else {
            throw new BussinesException("Não foi possível salvar configuração");
        }
    }

    private ConfigJogosDto parsedConfigJogoDto(ConfigJogosDomain saved) {
        return new ConfigJogosDto(
                saved.getId(),
                saved.getLevels().stream().map(level -> new NivelConfigDto(
                        level.getLevelName(),
                        level.getDeathTime(),
                        level.isMoreSuggestions(),
                        level.getMaxErrors(),
                        level.isHintsAllowed(),
                        level.isTimer(),
                        level.getChaosMode() != null ? new ModoCaosDto(
                                level.getChaosMode().getShuffleInterval(),
                                level.getChaosMode().getDisappearanceInterval(),
                                level.getChaosMode().getInitialDelay(),
                                level.getChaosMode().isHardcoreMode(),
                                level.getChaosMode().isStrategicMode()
                        ) : null
                )).collect(Collectors.toList())
        );
    }

    public List<ConfigJogosDomain> findAllConfig() {
        return configRepository.findAll();
    }

    public ConfigJogosDto retornaConfiguracao(String id) throws BussinesException {
        Optional<ConfigJogosDomain> optionalConfigJogos = configRepository.findById(id);
        ConfigJogosDomain configJogosDomain = optionalConfigJogos.get();

        return parsedConfigJogoDto(configJogosDomain);
    }
}

