package com.pissuto.forca.app.to.ConfigJogosTo;

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
    @JsonProperty("levels")
    private List<NivelConfigTo> levels;
}
