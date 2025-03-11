package com.pissuto.forca.app.to.ConfigJogosTo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NivelConfigTo {
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
