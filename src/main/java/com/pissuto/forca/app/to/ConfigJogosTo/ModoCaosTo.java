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
public class ModoCaosTo {
    @JsonProperty("shuffleInterval")
    private int shuffleInterval;

    @JsonProperty("disappearanceInterval")
    private int disappearanceInterval;

    @JsonProperty("initialDelay")
    private int initialDelay;

    @JsonProperty("hardcoreMode")
    private boolean hardcoreMode;

    @JsonProperty("strategicMode")
    private boolean strategicMode;
}
