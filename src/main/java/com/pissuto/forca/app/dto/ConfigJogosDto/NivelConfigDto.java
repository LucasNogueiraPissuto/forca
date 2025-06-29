package com.pissuto.forca.app.dto.ConfigJogosDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NivelConfigDto {
    private String levelName;
    private int deathTime;
    private int maxErrors;
    private boolean hintsAllowed;
    private boolean timer;
    private ModoCaosDto chaosMode;
}
