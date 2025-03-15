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
@Document(collection = "words")
public class WordDomain {
    @Id
    private String id;

    private String palavra;

    private List<String> dicas;

    public void atualizar(String palavra, List<String> dicas) {
        this.palavra = palavra;
        this.dicas = dicas;
    }
}
