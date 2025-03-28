package com.pissuto.forca.domain.ConfigJogosDomain;

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
}

