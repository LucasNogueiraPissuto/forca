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
@Document(collection = "ForcaJogos")
public class ForcaJogoDomain {

    @Id
    private String gameId;

    private String palavraSecreta;

    private String palavraMascarada;

    private List<String> palpites;

    private int bodyPieces;

    private int maxErrors;

    private String status;

    public ForcaJogoDomain(String palavraSecreta, String palavraMascarada, List<String> palpites, int bodyPieces, int maxErrors, String status) {
        this.palavraSecreta = palavraSecreta;
        this.palavraMascarada = palavraMascarada;
        this.palpites = palpites;
        this.bodyPieces = bodyPieces;
        this.maxErrors = maxErrors;
        this.status = status;
    }
}
