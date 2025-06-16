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
    @JsonProperty("levelName")
    private String levelName;

    @JsonProperty("deathTime")
    private int deathTime;

    @JsonProperty("maxErrors")
    private int maxErrors;

    @JsonProperty("hintsAllowed")
    private boolean hintsAllowed;

    @JsonProperty("timer")
    private boolean timer;

    @JsonProperty("chaosMode")
    private ModoCaosTo chaosMode;
}
