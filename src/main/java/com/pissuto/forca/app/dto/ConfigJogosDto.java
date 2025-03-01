package com.pissuto.forca.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfigJogosDto {

    private String id;
    private List<NivelConfigDto> levels;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NivelConfigDto {
        private String levelName;
        private int deathTime;
        private int bodyPieces;
        private boolean moreSuggestions;
        private int maxErrors;
        private boolean hintsAllowed;
        private boolean timer;
        private ModoCaosDto chaosMode;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModoCaosDto {
        private int shuffleInterval;
        private int disappearanceInterval;
        private int initialDelay;
        private boolean hardcoreMode;
        private boolean strategicMode;
    }
}
