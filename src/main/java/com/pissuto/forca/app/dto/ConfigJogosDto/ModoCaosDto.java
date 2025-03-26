package com.pissuto.forca.app.dto.ConfigJogosDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModoCaosDto {
    private int shuffleInterval;
    private int disappearanceInterval;
    private int initialDelay;
    private boolean hardcoreMode;
    private boolean strategicMode;
}