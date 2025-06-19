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
@Document(collection = "ForcaJogos")
public class ForcaJogoDomain {

    private int gameId;

    private String palavraSecreta;

    private String wordId;

    private String palavraMascarada;

    private List<String> dicas;

    private int dicasUsadas = 0;

    private List<String> palpites;

    private int maxErrors;

    private String status;

    public ForcaJogoDomain(int gameId, String palavraSecreta, String wordId, String palavraMascarada, List<String> dicas, int dicasUsadas, List<String> palpites, int maxErrors, String status) {
        this.gameId = gameId;
        this.palavraSecreta = palavraSecreta;
        this.wordId = wordId;
        this.palavraMascarada = palavraMascarada;
        this.dicas = dicas;
        this.dicasUsadas = dicasUsadas;
        this.palpites = palpites;
        this.maxErrors = maxErrors;
        this.status = status;
    }
}
