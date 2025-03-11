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
