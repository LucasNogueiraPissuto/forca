package com.pissuto.forca.domain.ConfigJogosDomain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NivelConfigDomain {
    private String levelName;
    private int deathTime;
    private int maxErrors;
    private boolean hintsAllowed;
    private boolean timer;
    private ModoCaosDomain chaosMode;
}
