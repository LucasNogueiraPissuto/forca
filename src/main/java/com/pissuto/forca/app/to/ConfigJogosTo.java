package com.pissuto.forca.app.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfigJogosTo {
    @JsonProperty("level")
    private List<NivelConfigTo> levels;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NivelConfigTo {
        @JsonProperty("level-name")
        private String levelName;

        @JsonProperty("death-time")
        private int deathTime;

        @JsonProperty("body-pieces")
        private int bodyPieces;

        @JsonProperty("more-suggestions")
        private boolean moreSuggestions;

        @JsonProperty("max-errors")
        private int maxErrors;

        @JsonProperty("hints-allowed")
        private boolean hintsAllowed;

        @JsonProperty("timer")
        private boolean timer;

        @JsonProperty("chaos-mode")
        private ModoCaosTo chaosMode;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModoCaosTo {
        @JsonProperty("shuffle-interval")
        private int shuffleInterval;

        @JsonProperty("disappearance-interval")
        private int disappearanceInterval;

        @JsonProperty("initial-delay")
        private int initialDelay;

        @JsonProperty("hardcore-mode")
        private boolean hardcoreMode;

        @JsonProperty("strategic-mode")
        private boolean strategicMode;
    }
}
