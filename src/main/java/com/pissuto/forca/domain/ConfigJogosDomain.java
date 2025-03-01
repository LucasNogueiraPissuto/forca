package com.pissuto.forca.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "configuration")
public class ConfigJogosDomain {
    @Id
    private String id;

    private List<NivelConfigDomain> levels;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NivelConfigDomain {
        private String levelName;
        private int deathTime;
        private int bodyPieces;
        private boolean moreSuggestions;
        private int maxErrors;
        private boolean hintsAllowed;
        private boolean timer;
        private ModoCaosDomain chaosMode;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModoCaosDomain {
        private int shuffleInterval;
        private int disappearanceInterval;
        private int initialDelay;
        private boolean hardcoreMode;
        private boolean strategicMode;
    }
}

